package com.example.myapplicationproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import android.content.Intent
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val loginbtn = findViewById<Button>(R.id.btnLogin)
        val crearbtn = findViewById<Button>(R.id.btnRegister)

        loginbtn.setOnClickListener {
            auth.signInWithEmailAndPassword(
                /* p0 = */ email.text.toString(),
                /* p1 = */ password.text.toString()
            ).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error: ${it.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        crearbtn.setOnClickListener {
            startActivity(Intent(this, crear_cuenta::class.java))
        }
        }

    }
