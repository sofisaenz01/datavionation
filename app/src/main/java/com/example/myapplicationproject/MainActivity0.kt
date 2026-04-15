package com.example.myapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity0 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main0)

        // Este es el botón "Ingresar" que está en tu cabecera
        val boton1 = findViewById<Button>(R.id.btningresar)

        boton1.setOnClickListener {
            // Te lleva a la pantalla de Login (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // HE ELIMINADO LA VARIABLE BOTON2 PORQUE YA NO EXISTE EN TU XML
    }
}