package com.inux.roomdatabaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inux.roomdatabaseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textTeste.text = "Olá galera"
    }
}