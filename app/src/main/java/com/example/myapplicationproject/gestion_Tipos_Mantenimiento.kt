package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class gestion_Tipos_Mantenimiento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_tipos_mantenimiento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarNavegacion()
    }

    private fun configurarNavegacion() {

        //Home
        findViewById<ImageButton>(R.id.btnNavHome).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // 🔴 Cambia por tu Activity de Home
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Vuelos
        findViewById<ImageButton>(R.id.btnNavVuelos).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // 🔴 Cambia por tu Activity de Vuelos
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Aeronaves
        findViewById<ImageButton>(R.id.btnNavAeronaves).setOnClickListener {
            val intent = Intent(this, flota_aeronave::class.java) // 🔴 Cambia por tu Activity de Aeronaves
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        //Mantenimiento
        findViewById<ImageButton>(R.id.btnNavMantenimiento).setOnClickListener {
            // Ya estamos aquí, no hace nada
        }

        // Perfil
        findViewById<ImageButton>(R.id.btnNavPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // 🔴 Cambia por tu Activity de Perfil
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}