package com.example.cubaturnicklesa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cubaturnicklesa.databinding.ActivityStandartsBinding
import org.jetbrains.annotations.Nullable
import org.json.JSONObject


@Suppress("DEPRECATION")
class StandartsCalculation : AppCompatActivity() {

    lateinit var binding: ActivityStandartsBinding
    val API_KEY = "4a9b003c974b48a8b24105729220712"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStandartsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var tab_toolbar = binding.toolbar
        var tab_viewpager = binding.tabViewpager
        var tab_tablayout = binding.tablayout

        setSupportActionBar(tab_toolbar)
        setupViewPager(tab_viewpager)

        tab_tablayout.setupWithViewPager(tab_viewpager)

        val actionBar = supportActionBar
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.apiWeather -> {
                getApi("Novokievsky Uval")
                return true
            }
            R.id.mainMenu -> {
                val db = DataBaseCubage.getDB(this)
                binding = ActivityStandartsBinding.inflate(layoutInflater)

                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Выход")
                dialog.setView(R.layout.dialog_exit)

                dialog.setPositiveButton("Да") { dialogInterface, which ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                dialog.setNegativeButton("Без сохранения") { dialogInterface, which ->
                    val runnable2 = Runnable {
                        var idClient = db.getDao().getLastIdClient()
                        db.getDao().deleteFromDataStandart(idClient)
                        db.getDao().deleteClients(idClient)
                    }
                    val thread = Thread(runnable2)
                    thread.start()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                dialog.setNeutralButton("Отмена") { dialogInterface, which ->
                    Toast.makeText(this, "Продолжайте редактировать...", Toast.LENGTH_LONG).show()
                }
                val alertDialog: AlertDialog = dialog.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(FragmentOpis(), "Описание")
        adapter.addFragment(FragmentCalc(), "Расчет")
        adapter.addFragment(FragmentItogi(), "Итоги")

        viewpager.adapter = adapter
        val fm = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fragcalc)
        if (fragment == null) {
            fragment = adapter.getItem(1)
            val bundle = Bundle()
            val arguments = intent.extras
            val name1 = arguments?.get("Gost2708").toString()
            val name2 = arguments?.get("ISO4480").toString()

            if (name1 == "101") {
                bundle.putString("Gost2708", name1)
            } else if (name2 == "102") {
                bundle.putString("ISO4480", name2)
            }
            Log.d("qw", "$name1 $name2")
            fragment.arguments = bundle
        }
    }

    class ViewPagerAdapter : FragmentPagerAdapter {

        var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        constructor(supportFragmentManager: FragmentManager) : super(supportFragmentManager)

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        override fun getCount(): Int {
            return fragmentList1.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }

    }

    private fun getApi(name: String){
        val url = "https://api.weatherapi.com/v1/current.json" +
                "?key=$API_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val runnable = Runnable {
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    val obj = JSONObject(response)
                    val temp = obj.getJSONObject("current")
                    Log.d("qw", "Response: ${temp.getString("temp_c")}")
                    Toast.makeText(this, "Температура в Новокиевском Увале ${temp.getString("temp_c")}°C", Toast.LENGTH_LONG).show()

                },
                {
                    Log.d("qw", "Volley error: $it")
                }
            )
        queue.add(stringRequest)
        }
        val thread = Thread(runnable)
        thread.start()
    }

}