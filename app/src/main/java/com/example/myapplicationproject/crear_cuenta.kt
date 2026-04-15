package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class crear_cuenta : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)

        // Inicializar Firebase Auth y Realtime Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etCelular = findViewById<EditText>(R.id.etCelular)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnRegistrar.setOnClickListener {
            val email = etCorreo.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid

                            // Creamos el mapa con los datos del usuario
                            val userMap = hashMapOf(
                                "nombre" to etNombre.text.toString(),
                                "apellido" to etApellido.text.toString(),
                                "celular" to etCelular.text.toString(),
                                "correo" to email
                            )

                            // GUARDAR EN REALTIME DATABASE (La de tu imagen)
                            userId?.let {
                                database.child("usuarios").child(it).setValue(userMap)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "¡Registro Exitoso en DataAviation!", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}