package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Ajuste de insets para diseño Edge-to-Edge
        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // --- REFERENCIAS DE UI (HEADER) ---
        // ID Unificado: btnCerrarSesion
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnPerfilHeader = findViewById<ImageButton>(R.id.btnPerfil)

        // --- REFERENCIAS DE TARJETAS ---
        val tvTotalAeronaves = findViewById<TextView>(R.id.tvTotalAeronaves)
        val tvEnVuelo = findViewById<TextView>(R.id.tvEnVuelo)
        val tvAlertas = findViewById<TextView>(R.id.tvAlertasCriticas)

        // --- NAVEGACIÓN INFERIOR ---
        val btnNavHome = findViewById<ImageButton>(R.id.btnNavHome)
        val btnNavVuelos = findViewById<ImageButton>(R.id.btnNavVuelos)
        val btnNavAeronaves = findViewById<ImageButton>(R.id.btnNavAeronaves)
        val btnNavMantenimiento = findViewById<ImageButton>(R.id.btnNavMantenimiento)
        val btnNavPerfil = findViewById<ImageButton>(R.id.btnNavPerfil)

        // =============================================
        // --- LÓGICA DE BOTONES ---
        // =============================================

        // Cerrar sesión con validación de nulidad
        btnCerrarSesion?.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity0::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnPerfilHeader?.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }

        // =============================================
        // --- NAVEGACIÓN INFERIOR ---
        // =============================================

        btnNavHome?.setOnClickListener {
            Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show()
        }

        btnNavVuelos?.setOnClickListener {
            startActivity(Intent(this, registro_vuelo::class.java))
        }

        btnNavAeronaves?.setOnClickListener {
            startActivity(Intent(this, flota_aeronave::class.java))
        }

        btnNavMantenimiento?.setOnClickListener {
            startActivity(Intent(this, gestion_mantenimiento::class.java))
        }

        btnNavPerfil?.setOnClickListener {
            startActivity(Intent(this, gestion_pilotos::class.java))
        }
    }
}