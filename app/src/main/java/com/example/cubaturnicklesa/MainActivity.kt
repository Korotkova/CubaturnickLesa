package com.example.cubaturnicklesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cubaturnicklesa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val intent = Intent(this, StandartsCalculation::class.java)
            intent.putExtra("Gost2708", "101")
            startActivity(intent)
        }
        binding.button2.setOnClickListener{
            val intent = Intent(this, StandartsCalculation::class.java)
            intent.putExtra("ISO4480", "102")
            startActivity(intent)
        }
        binding.button5.setOnClickListener{
            val intent = Intent(this, ArhivActivity::class.java)
            startActivity(intent)
        }
    }
}