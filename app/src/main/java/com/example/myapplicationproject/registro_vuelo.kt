package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class registro_vuelo : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    // Listas para los spinners
    private val listaAeronaves   = mutableListOf<String>() // Matrículas visibles
    private val idsAeronaves     = mutableListOf<String>() // Keys de Firebase (aeronave_001...)
    private val listaPilotos     = mutableListOf<String>() // Nombres visibles
    private val idsPilotos       = mutableListOf<String>() // Keys de Firebase (piloto_001...)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_vuelo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ── Inicializar Firebase ──────────────────────────────────────
        database = FirebaseDatabase.getInstance().reference

        // ── Referencias de vistas ─────────────────────────────────────
        val spinnerAeronave  = findViewById<Spinner>(R.id.spinnerAeronave)
        val spinnerPiloto    = findViewById<Spinner>(R.id.spinnerPiloto)
        val etFecha          = findViewById<EditText>(R.id.etFecha)
        val etHobbsInicial   = findViewById<EditText>(R.id.etHobbsInicial)
        val etHobbsFinal     = findViewById<EditText>(R.id.etHobbsFinal)
        val btnRegistrar     = findViewById<Button>(R.id.btnRegistrarVuelo)

        // ── Cargar Aeronaves desde Firebase ──────────────────────────
        database.child("aeronaves").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaAeronaves.clear()
                idsAeronaves.clear()
                for (aeronave in snapshot.children) {
                    val matricula = aeronave.child("matricula").getValue(String::class.java)
                    if (matricula != null) {
                        listaAeronaves.add(matricula)
                        idsAeronaves.add(aeronave.key ?: "")
                    }
                }
                val adapterAero = ArrayAdapter(
                    this@registro_vuelo,
                    android.R.layout.simple_spinner_dropdown_item,
                    listaAeronaves
                )
                spinnerAeronave.adapter = adapterAero
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@registro_vuelo,
                    "Error cargando aeronaves: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // ── Cargar Pilotos desde Firebase ────────────────────────────
        database.child("pilotos").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaPilotos.clear()
                idsPilotos.clear()
                for (piloto in snapshot.children) {
                    val nombre   = piloto.child("nombre").getValue(String::class.java) ?: ""
                    val apellido = piloto.child("apellido").getValue(String::class.java) ?: ""
                    listaPilotos.add("$nombre $apellido")
                    idsPilotos.add(piloto.key ?: "")
                }
                val adapterPiloto = ArrayAdapter(
                    this@registro_vuelo,
                    android.R.layout.simple_spinner_dropdown_item,
                    listaPilotos
                )
                spinnerPiloto.adapter = adapterPiloto
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@registro_vuelo,
                    "Error cargando pilotos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // ── Botón Registrar Vuelo ─────────────────────────────────────
        btnRegistrar.setOnClickListener {

            val fecha        = etFecha.text.toString().trim()
            val hInicialStr  = etHobbsInicial.text.toString().trim()
            val hFinalStr    = etHobbsFinal.text.toString().trim()

            // Validaciones
            when {
                fecha.isEmpty() -> {
                    etFecha.error = "Ingresa la fecha del vuelo"
                    etFecha.requestFocus()
                    return@setOnClickListener
                }
                hInicialStr.isEmpty() -> {
                    etHobbsInicial.error = "Ingresa el Hobbs inicial"
                    etHobbsInicial.requestFocus()
                    return@setOnClickListener
                }
                hFinalStr.isEmpty() -> {
                    etHobbsFinal.error = "Ingresa el Hobbs final"
                    etHobbsFinal.requestFocus()
                    return@setOnClickListener
                }
                listaAeronaves.isEmpty() -> {
                    Toast.makeText(this, "No hay aeronaves disponibles", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                listaPilotos.isEmpty() -> {
                    Toast.makeText(this, "No hay pilotos disponibles", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val hInicial = hInicialStr.toDoubleOrNull()
            val hFinal   = hFinalStr.toDoubleOrNull()

            if (hInicial == null || hFinal == null) {
                Toast.makeText(this, "Los valores Hobbs deben ser numéricos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (hFinal < hInicial) {
                Toast.makeText(this, "Hobbs final debe ser mayor al inicial", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tiempoVuelo   = hFinal - hInicial
            val idAeronave    = idsAeronaves[spinnerAeronave.selectedItemPosition]
            val idPiloto      = idsPilotos[spinnerPiloto.selectedItemPosition]

            // ── Mapa que coincide con tu BD ───────────────────────────
            val vueloMap = hashMapOf(
                "id_aeronave"   to idAeronave,
                "id_piloto"     to idPiloto,
                "fecha_vuelo"   to fecha,
                "hobbs_inicial" to hInicial,
                "hobbs_final"   to hFinal,
                "tiempo_vuelo"  to tiempoVuelo,
                "ciclos_vuelo"  to 1,
                "estado"        to "Completado"
            )

            // ── Guardar en Firebase con key automático ────────────────
            val nuevoVueloRef = database.child("vuelos").push()
            nuevoVueloRef.setValue(vueloMap)
                .addOnSuccessListener {
                    // Actualizar horas totales de la aeronave
                    actualizarHorasAeronave(idAeronave, tiempoVuelo)

                    Toast.makeText(
                        this,
                        "✅ Vuelo registrado: %.1f horas".format(tiempoVuelo),
                        Toast.LENGTH_LONG
                    ).show()
                    limpiarCampos(etFecha, etHobbsInicial, etHobbsFinal)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this,
                        "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        // ── Navegación inferior ───────────────────────────────────────
        configurarNavegacion()
    }

    // ── Actualiza horas_vuelo_totales de la aeronave en Firebase ─────
    private fun actualizarHorasAeronave(idAeronave: String, horasNuevas: Double) {
        val refAeronave = database.child("aeronaves").child(idAeronave)
        refAeronave.child("horas_vuelo_totales")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val horasActuales = snapshot.getValue(Double::class.java) ?: 0.0
                    val horasActualizadas = horasActuales + horasNuevas
                    refAeronave.child("horas_vuelo_totales").setValue(horasActualizadas)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    // ── Limpiar campos después de registrar ──────────────────────────
    private fun limpiarCampos(vararg campos: EditText) {
        for (campo in campos) campo.text.clear()
    }

    // ── Navegación inferior ───────────────────────────────────────────
    private fun configurarNavegacion() {
        findViewById<ImageButton>(R.id.btnNavHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)) // 🔴 Cambia por tu Home
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        findViewById<ImageButton>(R.id.btnNavVuelos).setOnClickListener {
            // Ya estamos aquí
        }
        findViewById<ImageButton>(R.id.btnNavAeronaves).setOnClickListener {
            startActivity(Intent(this, flota_aeronave::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        findViewById<ImageButton>(R.id.btnNavConfiguracion).setOnClickListener {
            startActivity(Intent(this, gestion_Tipos_Mantenimiento::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        findViewById<ImageButton>(R.id.btnNavPerfil).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)) // 🔴 Cambia por tu Perfil
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }
}