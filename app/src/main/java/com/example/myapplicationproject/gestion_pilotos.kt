package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class gestion_pilotos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_pilotos)

        // Ajuste de insets para diseño limpio
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- REFERENCIAS DE UI (HEADER) ---
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnPerfilHeader = findViewById<ImageButton>(R.id.btnPerfil)
        val scrollView = findViewById<ScrollView>(R.id.scrollView)

        // --- REFERENCIAS DE BOTONES DE TABLA ---
        val btnVerHistorialJuan = findViewById<Button>(R.id.btnVerHistorialJuan)
        val btnVerHistorialMaria = findViewById<Button>(R.id.btnVerHistorialMaria)

        // --- NAVEGACIÓN INFERIOR ---
        val btnNavHome = findViewById<ImageButton>(R.id.btnNavHome)
        val btnNavVuelos = findViewById<ImageButton>(R.id.btnNavVuelos)
        val btnNavAeronaves = findViewById<ImageButton>(R.id.btnNavAeronaves)
        val btnNavMantenimiento = findViewById<ImageButton>(R.id.btnNavMantenimiento)

        // --- LÓGICA DE INTERACCIÓN ---

        // Al presionar Juan, bajamos el scroll hasta su historial
        btnVerHistorialJuan.setOnClickListener {
            // Buscamos la posición del título de Juan y hacemos scroll
            scrollView.post {
                scrollView.smoothScrollTo(0, 500) // Ajusta el valor según tu diseño
            }
        }

        // Al presionar Maria, bajamos el scroll más abajo
        btnVerHistorialMaria.setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, 1000) // Ajusta el valor según tu diseño
            }
        }

        // --- FUNCIONALIDAD HEADER ---

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnPerfilHeader.setOnClickListener {
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

        btnNavMantenimiento.setOnClickListener {
            startActivity(Intent(this, gestion_mantenimiento::class.java))
        }
    }
}