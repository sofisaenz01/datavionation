package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class gestion_mantenimiento : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_mantenimiento)

        // Ajuste de insets para diseño Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- REFERENCIAS DE UI ---

        // Header
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnPerfilIcono = findViewById<ImageButton>(R.id.btnPerfil)

        // Formulario (Spinners)
        val spinnerAeronave = findViewById<Spinner>(R.id.spinnerAeronave)
        val spinnerTipoInspeccion = findViewById<Spinner>(R.id.spinnerTipoInspeccion)
        val btnGuardarMantenimiento = findViewById<Button>(R.id.btnGuardarMantenimiento)
        val btnLiberarAeronave = findViewById<Button>(R.id.btnLiberarAeronave)

        // Navegación Inferior
        val btnNavHome = findViewById<ImageButton>(R.id.btnNavHome)
        val btnNavVuelos = findViewById<ImageButton>(R.id.btnNavVuelos)
        val btnNavAeronaves = findViewById<ImageButton>(R.id.btnNavAeronaves)
        val btnNavPerfil = findViewById<ImageButton>(R.id.btnNavPerfil)

        // --- CONFIGURACIÓN DE SPINNERS (EJEMPLO) ---

        val listaAeronaves = arrayOf("Seleccione aeronave", "HIK-5678", "HK-1234", "HK-9988")
        val adapterAero = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaAeronaves)
        spinnerAeronave.adapter = adapterAero

        val listaInspecciones = arrayOf("Seleccione tipo", "100 horas", "Anual", "Preventiva")
        val adapterInsp = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaInspecciones)
        spinnerTipoInspeccion.adapter = adapterInsp

        // --- LÓGICA DE BOTONES ---

        btnGuardarMantenimiento.setOnClickListener {
            Toast.makeText(this, "Mantenimiento Guardado Correctamente", Toast.LENGTH_SHORT).show()
        }

        btnLiberarAeronave.setOnClickListener {
            Toast.makeText(this, "Aeronave Liberada para Vuelo", Toast.LENGTH_LONG).show()
        }

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, crear_cuenta::class.java)
            startActivity(intent)
            finish()
        }

        btnPerfilIcono.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }

        // --- NAVEGACIÓN INFERIOR ---

        btnNavHome.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        btnNavVuelos.setOnClickListener {
            startActivity(Intent(this, registro_vuelo::class.java))
        }

        btnNavAeronaves.setOnClickListener {
            startActivity(Intent(this, flota_aeronave::class.java))
        }

        btnNavPerfil.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }
    }
}