package com.example.cubaturnicklesa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextClock
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.cubaturnicklesa.databinding.FragmentCalcBinding
import com.example.cubaturnicklesa.databinding.FragmentOpisBinding

class FragmentOpis : Fragment() {

    lateinit var binding: FragmentOpisBinding
    var fioClient = ""
    var price: Double = 0.0
    var tipDereva = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpisBinding.inflate(inflater)
        return binding.root
    }

    //здесь получаем элементы фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DataBaseCubage.getDB(requireContext())

        val btn : Button = binding.saveOpis
        btn.setOnClickListener {
            fioClient = binding.editFioClient.text.toString()
            price = binding.editPrice.text.toString().toDouble()
            tipDereva = binding.editTipDereva.text.toString()

            val client = Clients(null, fioClient,price,tipDereva,binding.textClock2.text.toString())
            Thread{
                db.getDao().insertClients(client)
            }.start()
            Toast.makeText(context, "Клиент создан. Нажми на 'Расчет'", Toast.LENGTH_LONG).show()
        }
    }
}