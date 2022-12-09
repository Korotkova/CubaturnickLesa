package com.example.cubaturnicklesa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cubaturnicklesa.databinding.DialogBinding
import com.example.cubaturnicklesa.databinding.FragmentCalcBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class FragmentCalc : Fragment() {

    lateinit var bindingF: FragmentCalcBinding
    lateinit var dialogFab: DialogBinding
    var dlinaET = 0.0
    var diametrET = 0
    var countET = 0
    private var resh: Double = 0.0
    var cubResult: Double = 0.0
    private val adapter = CalcDialogAdapter()
    var myValue1: String = ""
    var myValue2: String = ""
    var standart: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myValue1 = arguments?.getString("Gost2708").toString()
        myValue2 = arguments?.getString("ISO4480").toString()
        if(myValue1 == "101"){
            standart = "standart2708"
        }
        if(myValue2 == "102"){
            standart = "iso4480"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingF = FragmentCalcBinding.inflate(inflater)

        return bindingF.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DataBaseCubage.getDB(requireContext())

        val fab: FloatingActionButton = bindingF.fab
        fab.setOnClickListener {
            dialogFab = DialogBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Параметры бревна:")
            dialog.setView(dialogFab.root)

            dialog.setNeutralButton("Отмена") { dialogInterface, which ->
                Toast.makeText(context, "Данные НЕ сохранены!", Toast.LENGTH_LONG).show()
            }
            dialog.setPositiveButton("OK") { dialogInterface, which ->
                dlinaET = dialogFab.dlinaEdText.text.toString().toDouble()
                diametrET = dialogFab.diametrEdText.text.toString().toInt()
                countET = dialogFab.countEdText.text.toString().toInt()
                cubResult = findStandart(dlinaET, diametrET, countET)

                //вывод в RecycleView
                bindingF.apply {
                    rcView.layoutManager = LinearLayoutManager(context)
                    rcView.adapter = adapter
                }
                val calcDialogAdapter = CalcDialogForRecycleView(dlinaET, diametrET, countET, cubResult)
                adapter.addDataFromDialog(calcDialogAdapter)

                Toast.makeText(context, "Данные записаны!", Toast.LENGTH_LONG).show()

                Thread {
                    var idClient = db.getDao().getLastIdClient()
                    val standart = DataStandarts(
                        null,
                        dlinaET,
                        diametrET,
                        countET,
                        cubResult,
                        standart,
                        idClient
                    )
                    db.getDao().insertDataStandarts(standart)
                }.start()

            }

            val alertDialog: AlertDialog = dialog.create()
            alertDialog.setCancelable(false)
            alertDialog.setContentView(R.layout.dialog)
            alertDialog.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentCalc()
    }


    fun findStandart(dlina: Double, diametr: Int, count: Int): Double {
        val standartOt1do4i9 = StandartOt1do4i9()
        val standartOt5do8i9 = StandartOt5do8i9()
        val isoOt1do4i9 = ISOOt1do4i9()
        val isoOt5do8i9 = ISOOt5do8i9()

        if (myValue1 == "101") {
            if (dlina <= 4.9) {
                for (i in standartOt1do4i9.standart.indices) {
                    var j = 0
                    while (j < standartOt1do4i9.standart[i].size) {
                        if (standartOt1do4i9.standart[0][j] == dlina && standartOt1do4i9.standart[i][0] == diametr.toDouble()) {
                            resh = standartOt1do4i9.standart[i][j]
                        }
                        j++
                    }
                }
            } else if (dlina > 4.9 && dlina <= 8.9) {
                for (i in standartOt5do8i9.standart.indices) {
                    var j = 0
                    while (j < standartOt5do8i9.standart[i].size) {
                        if (standartOt5do8i9.standart[0][j] == dlina && standartOt5do8i9.standart[i][0] == diametr.toDouble()) {
                            resh = standartOt5do8i9.standart[i][j]
                        }
                        j++
                    }
                }
            }
        }else if(myValue2 == "102"){
            if(dlina <= 1.9){
                for (i in isoOt1do4i9.iso1019.indices) {
                    var j = 0
                    while (j < isoOt1do4i9.iso1019[i].size) {
                        if (isoOt1do4i9.iso1019[0][j] == dlina && isoOt1do4i9.iso1019[i][0] == diametr.toDouble()) {
                            resh = isoOt1do4i9.iso1019[i][j]
                        }
                    }
                }
            }else if(dlina > 1.9 && dlina <= 2.9){
                for (i in isoOt1do4i9.iso2029.indices) {
                    var j = 0
                    while (j < isoOt1do4i9.iso2029[i].size) {
                        if (isoOt1do4i9.iso2029[0][j] == dlina && isoOt1do4i9.iso2029[i][0] == diametr.toDouble()) {
                            resh = isoOt1do4i9.iso2029[i][j]
                        }
                    }
                }
            }else if(dlina > 2.9 && dlina <= 3.9){
                for (i in isoOt1do4i9.iso3039.indices) {
                    var j = 0
                    while (j < isoOt1do4i9.iso3039[i].size) {
                        if (isoOt1do4i9.iso3039[0][j] == dlina && isoOt1do4i9.iso3039[i][0] == diametr.toDouble()) {
                            resh = isoOt1do4i9.iso3039[i][j]
                        }
                    }
                }
            }else if (dlina > 3.9 && dlina <= 4.9) {
                for (i in isoOt1do4i9.iso4049.indices) {
                    var j = 0
                    while (j < isoOt1do4i9.iso4049[i].size) {
                        if (isoOt1do4i9.iso4049[0][j] == dlina && isoOt1do4i9.iso4049[i][0] == diametr.toDouble()) {
                            resh = isoOt1do4i9.iso4049[i][j]
                        }
                        j++
                    }
                }
            } else if (dlina > 4.9 && dlina <= 5.9) {
                for (i in isoOt5do8i9.iso5059.indices) {
                    var j = 0
                    while (j < isoOt5do8i9.iso6069[i].size) {
                        if (isoOt5do8i9.iso6069[0][j] == dlina && isoOt5do8i9.iso6069[i][0] == diametr.toDouble()) {
                            resh = isoOt5do8i9.iso6069[i][j]
                        }
                        j++
                    }
                }
            }else if (dlina > 6.9 && dlina <= 7.9) {
                for (i in isoOt5do8i9.iso7079.indices) {
                    var j = 0
                    while (j < isoOt5do8i9.iso7079[i].size) {
                        if (isoOt5do8i9.iso7079[0][j] == dlina && isoOt5do8i9.iso7079[i][0] == diametr.toDouble()) {
                            resh = isoOt5do8i9.iso7079[i][j]
                        }
                        j++
                    }
                }
            }else if (dlina > 7.9 && dlina <= 8.9) {
                for (i in isoOt5do8i9.iso8089.indices) {
                    var j = 0
                    while (j < isoOt5do8i9.iso8089[i].size) {
                        if (isoOt5do8i9.iso8089[0][j] == dlina && isoOt5do8i9.iso8089[i][0] == diametr.toDouble()) {
                            resh = isoOt5do8i9.iso8089[i][j]
                        }
                        j++
                    }
                }
            }
        }

        if (count > 1) {
            resh *= count
        }

        return resh
        }

    }

