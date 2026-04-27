package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Perfil : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        // Configuración de Insets para diseño borde a borde
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Referencias de los campos
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)

        // Referencias de los botones
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambios)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        // 1. CARGAR DATOS DEL USUARIO (Desde Firebase Auth y Firestore)
        val user = auth.currentUser
        if (user != null) {
            etCorreo.setText(user.email)
            etCorreo.isEnabled = false // El correo normalmente no se edita así por seguridad

            // Traer datos adicionales de Firestore (Nombre, Apellido, etc.)
            db.collection("usuarios").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        etNombre.setText(document.getString("nombre"))
                        etApellido.setText(document.getString("apellido"))
                        etTelefono.setText(document.getString("telefono"))
                    }
                }
        }

        // 2. BOTÓN GUARDAR CAMBIOS
        btnGuardar.setOnClickListener {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                val datosActualizados = hashMapOf(
                    "nombre" to etNombre.text.toString(),
                    "apellido" to etApellido.text.toString(),
                    "telefono" to etTelefono.text.toString()
                )

                db.collection("usuarios").document(uid).set(datosActualizados)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // 3. BOTÓN CANCELAR (Limpia o recarga la actividad)
        btnCancelar.setOnClickListener {
            recreate() // Reinicia la actividad para descartar cambios no guardados
        }

        // 4. BOTÓN ATRÁS
        btnAtras.setOnClickListener {
            finish() // Cierra esta pantalla y vuelve a la anterior (Dashboard)
        }

        // 5. NAVEGACIÓN INFERIOR (Solo ejemplo para el botón Home)
        findViewById<android.widget.ImageButton>(R.id.btnNavHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}