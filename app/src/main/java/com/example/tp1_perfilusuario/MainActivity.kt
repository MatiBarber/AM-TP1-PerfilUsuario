package com.example.tp1_perfilusuario

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val TAG = "LifecycleMain"
    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var tvFecha: TextView
    private lateinit var spinnerCarrera: Spinner
    private var fechaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        tvFecha = findViewById(R.id.tvFechaSeleccionada)
        spinnerCarrera = findViewById(R.id.spinnerCarrera)
        val btnFecha: Button = findViewById(R.id.btnFecha)
        val btnVerPerfil: Button = findViewById(R.id.btnVerPerfil)

        ArrayAdapter.createFromResource(
            this,
            R.array.carreras_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCarrera.adapter = adapter
        }

        // Si venimos de "Editar datos" en ProfileActivity, precargamos los valores
        intent.getStringExtra("EXTRA_NOMBRE")?.let { etNombre.setText(it) }
        intent.getStringExtra("EXTRA_APELLIDO")?.let { etApellido.setText(it) }
        intent.getStringExtra("EXTRA_FECHA")?.let {
            fechaSeleccionada = it
            tvFecha.text = it
        }
        val carreraPrevia = intent.getStringExtra("EXTRA_CARRERA")
        if (carreraPrevia != null) {
            spinnerCarrera.post {
                val adapter = spinnerCarrera.adapter as ArrayAdapter<String>
                val posicion = adapter.getPosition(carreraPrevia)
                if (posicion >= 0) spinnerCarrera.setSelection(posicion)
            }
        }

        btnFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                fechaSeleccionada = "%02d/%02d/%04d".format(d, m + 1, y)
                tvFecha.text = fechaSeleccionada
            }, year, month, day).show()
        }

        btnVerPerfil.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty()) {
                Toast.makeText(this, "Ingresá tu nombre y apellido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (fechaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Seleccioná tu fecha de nacimiento", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val carrera = spinnerCarrera.selectedItem.toString()

            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombre)
                putExtra("EXTRA_APELLIDO", apellido)
                putExtra("EXTRA_FECHA", fechaSeleccionada)
                putExtra("EXTRA_CARRERA", carrera)
            }
            startActivity(intent)
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}