package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class flota_aeronave : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flota_aeronave)

        // ── Inicializar Firebase ──────────────────────────────────────
        database = FirebaseDatabase.getInstance().reference

        // ── Referencias de UI (Header) ────────────────────────────────
        val btnPerfilHeader = findViewById<ImageButton>(R.id.btnPerfil)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarsesion)

        // ── Referencias de tabla ──────────────────────────────────────
        val btnDetalle1 = findViewById<Button>(R.id.btnDetalle1)
        val btnDetalle2 = findViewById<Button>(R.id.btnDetalle2)
        val btnDetalle3 = findViewById<Button>(R.id.btnDetalle3)

        val cardDetalle1 = findViewById<CardView>(R.id.cardDetalle1)
        val cardDetalle2 = findViewById<CardView>(R.id.cardDetalle2)
        val cardDetalle3 = findViewById<CardView>(R.id.cardDetalle3)

        // ── Referencias botones eliminar ──────────────────────────────
        val btnEliminar1 = findViewById<Button>(R.id.btnEliminarAeronave1)
        val btnEliminar2 = findViewById<Button>(R.id.btnEliminarAeronave2)
        val btnEliminar3 = findViewById<Button>(R.id.btnEliminarAeronave3)

        // ── Referencias navegación inferior ───────────────────────────
        val btnNavHome          = findViewById<ImageButton>(R.id.btnNavHome)
        val btnNavVuelos        = findViewById<ImageButton>(R.id.btnNavVuelos)
        val btnNavAeronaves     = findViewById<ImageButton>(R.id.btnNavAeronaves)
        val btnNavMantenimiento = findViewById<ImageButton>(R.id.btnNavMantenimiento)
        val btnNavPerfil        = findViewById<ImageButton>(R.id.btnNavPerfil)

        // ── IDs de aeronaves en Firebase (deben coincidir con tu BD) ──
        // Estos son los keys reales de tu Realtime Database
        val idAeronave1 = "aeronave_001" // HK-1234
        val idAeronave2 = "aeronave_002" // HK-5678
        val idAeronave3 = "aeronave_003" // HK-9012

        // ── Lógica mostrar/ocultar detalles ──────────────────────────
        fun ocultarTodos() {
            cardDetalle1.visibility = View.GONE
            cardDetalle2.visibility = View.GONE
            cardDetalle3.visibility = View.GONE
        }

        ocultarTodos()

        btnDetalle1.setOnClickListener { ocultarTodos(); cardDetalle1.visibility = View.VISIBLE }
        btnDetalle2.setOnClickListener { ocultarTodos(); cardDetalle2.visibility = View.VISIBLE }
        btnDetalle3.setOnClickListener { ocultarTodos(); cardDetalle3.visibility = View.VISIBLE }

        // ── Función reutilizable para eliminar aeronave ───────────────
        fun eliminarAeronave(
            idAeronave: String,
            matricula: String,
            card: CardView
        ) {
            // Diálogo de confirmación antes de eliminar
            AlertDialog.Builder(this)
                .setTitle("Eliminar Aeronave")
                .setMessage("¿Estás seguro de que deseas eliminar la aeronave $matricula? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar") { _, _ ->

                    database.child("aeronaves").child(idAeronave)
                        .removeValue()
                        .addOnSuccessListener {
                            // Ocultar la tarjeta después de eliminar
                            card.visibility = View.GONE

                            android.widget.Toast.makeText(
                                this,
                                "✅ Aeronave $matricula eliminada correctamente",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            android.widget.Toast.makeText(
                                this,
                                "Error al eliminar: ${e.message}",
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                        }
                }
                .setNegativeButton("Cancelar", null) // No hace nada al cancelar
                .show()
        }

        // ── Botones eliminar con confirmación ─────────────────────────
        btnEliminar1.setOnClickListener {
            eliminarAeronave(idAeronave1, "HK-1234", cardDetalle1)
        }

        btnEliminar2.setOnClickListener {
            eliminarAeronave(idAeronave2, "HK-5678", cardDetalle2)
        }

        btnEliminar3.setOnClickListener {
            eliminarAeronave(idAeronave3, "HK-9012", cardDetalle3)
        }

        // ── Header ────────────────────────────────────────────────────
        btnPerfilHeader.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // ── Navegación inferior ───────────────────────────────────────
        btnNavHome.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        btnNavVuelos.setOnClickListener {
            startActivity(Intent(this, registro_vuelo::class.java))
        }
        btnNavAeronaves.setOnClickListener {
            // Ya estás en esta pantalla
        }
        btnNavMantenimiento.setOnClickListener {
            startActivity(Intent(this, gestion_mantenimiento::class.java))
        }
        btnNavPerfil.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }
    }
}