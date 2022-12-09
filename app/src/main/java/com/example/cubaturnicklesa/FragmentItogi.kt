package com.example.cubaturnicklesa

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cubaturnicklesa.databinding.DialogBinding
import com.example.cubaturnicklesa.databinding.DialogExitBinding
import com.example.cubaturnicklesa.databinding.FragmentItogiBinding


class FragmentItogi : Fragment() {

    private lateinit var dialogFab: DialogExitBinding
    lateinit var binding: FragmentItogiBinding
    private var adapter = ItogAdapter()
    private var count = 0
    private var cubage = 0.0
    private var price = 0.0
    private var priceResult = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItogiBinding.inflate(inflater)
        return binding.root
    }

    //здесь получаем элементы фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DataBaseCubage.getDB(requireContext())

        var btn: Button = binding.button6
        btn.setOnClickListener {
            val runnable = Runnable {
                val msg: Message = handler.obtainMessage()
                val bundle = Bundle()

                var idClient = db.getDao().getLastIdClient()
                val cursor: Cursor = db.getDao().getData(idClient)
                cursor.moveToFirst()
                if (!cursor.isAfterLast) {
                    do {
                        count += cursor.getInt(8)
                        cubage += cursor.getDouble(9)
                        price = cursor.getDouble(2)

                        priceResult += price * cursor.getDouble(8)

                        val itogAdapter = ItogForRecycleView(
                            cursor.getDouble(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getDouble(9),
                            (price * cursor.getDouble(8))
                        )
                        adapter.addDataFromDialog(itogAdapter)
                    } while (cursor.moveToNext())
                }

                val cub = String.format("%.3f", cubage)
                bundle.putString("price", priceResult.toString())
                bundle.putString("countBrevno", count.toString())
                bundle.putString("cubage", cub)

                cursor.close()

                msg.data = bundle;
                handler.sendMessage(msg);
            }
            val thread = Thread(runnable)
            thread.start()
            adapter = ItogAdapter()
            cubage = 0.0
            priceResult = 0.0
            count = 0
        }
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            binding.rcView2.layoutManager = LinearLayoutManager(context)
            binding.rcView2.adapter = adapter // = null
            val bundle = msg.data
            var price = bundle.getString("price")
            val countb = bundle.getString("countBrevno")
            val cubage = bundle.getString("cubage")

            binding.textView15.text = "$price ₽"
            binding.textView2.text = "$countb шт."
            binding.textView14.text = "$cubage м³"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentItogi()
    }
}