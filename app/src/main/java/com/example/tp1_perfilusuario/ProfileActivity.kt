package com.example.tp1_perfilusuario

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {

    private val tagLog = "LifecycleProfile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(tagLog, "onCreate")

        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: ""
        val apellido = intent.getStringExtra("EXTRA_APELLIDO") ?: ""
        val fecha = intent.getStringExtra("EXTRA_FECHA") ?: ""
        val carrera = intent.getStringExtra("EXTRA_CARRERA") ?: ""

        val tvSaludo: TextView = findViewById(R.id.tvSaludo)
        val tvEdad: TextView = findViewById(R.id.tvEdad)
        val tvCarrera: TextView = findViewById(R.id.tvCarrera)
        val btnEditar: Button = findViewById(R.id.btnEditar)
        val btnVolver: Button = findViewById(R.id.btnVolver)

        tvSaludo.text = "¡Hola, $nombre $apellido!"
        tvEdad.text = "Edad: ${calcularEdad(fecha)} años (nacido el $fecha)"
        tvCarrera.text = "Carrera / Lenguaje favorito: $carrera"

        btnEditar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombre)
                putExtra("EXTRA_APELLIDO", apellido)
                putExtra("EXTRA_FECHA", fecha)
                putExtra("EXTRA_CARRERA", carrera)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
            finish()
        }

        btnVolver.setOnClickListener { finish() }
    }

    private fun calcularEdad(fecha: String): Int {
        return try {
            val partes = fecha.split("/")
            val dia = partes[0].toInt()
            val mes = partes[1].toInt()
            val anio = partes[2].toInt()

            val hoy = Calendar.getInstance()
            var edad = hoy.get(Calendar.YEAR) - anio
            val mesActual = hoy.get(Calendar.MONTH) + 1
            val diaActual = hoy.get(Calendar.DAY_OF_MONTH)

            if (mesActual < mes || (mesActual == mes && diaActual < dia)) {
                edad--
            }
            edad
        } catch (e: Exception) {
            0
        }
    }

    override fun onStart() { super.onStart(); Log.d(tagLog, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tagLog, "onResume") }
    override fun onPause() { super.onPause(); Log.d(tagLog, "onPause") }
    override fun onStop() { super.onStop(); Log.d(tagLog, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(tagLog, "onDestroy") }
}