package com.example.cubaturnicklesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cubaturnicklesa.databinding.ActivityArhivBinding

class ArhivActivity : AppCompatActivity() {

    var positionRecycle = ""
    var adapter = ArhivAdapter {
    }
    lateinit var binding: ActivityArhivBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArhivBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DataBaseCubage.getDB(this)

        val btn: Button = binding.update
        btn.setOnClickListener {
            //получить список клиентов
            db.getDao().getAllClients().asLiveData().observe(this) { list ->
                binding.arhivRecycle.layoutManager = LinearLayoutManager(this)
                binding.arhivRecycle.adapter = adapter
                list.forEach {
                    val fio = it.fio_clients
                    val data = it.data
                    val arhivAdapter = ArhivForRecycleView(fio, data)
                    adapter.addDataFromDialog(arhivAdapter)
                }
            }
            adapter = ArhivAdapter {
                positionRecycle = it
                Log.d("qw", positionRecycle)
            }
        }


    }
}