package com.example.movilcofee

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // TEXTVIEWS
        val temperatura = findViewById<TextView>(R.id.txtTemperatura)
        val estado = findViewById<TextView>(R.id.txtEstado)
        val tipo = findViewById<TextView>(R.id.txtTipo)

        // BOTONES
        val boton = findViewById<Button>(R.id.btnDetener)
        val btnLigero = findViewById<Button>(R.id.btnLigero)
        val btnMedio = findViewById<Button>(R.id.btnMedio)
        val btnOscuro = findViewById<Button>(R.id.btnOscuro)

        // VARIABLES
        var tempActual = 120
        var tostando = false
        var tipoSeleccionado = "Medio"

        val handler = Handler(Looper.getMainLooper())

        // RUNNABLE
        val runnable = object : Runnable {

            override fun run() {

                if (tostando) {

                    tempActual += 2

                    temperatura.text = "$tempActual°C"

                    when (tipoSeleccionado) {

                        "Ligero" -> {

                            tipo.text = "Ligero"

                            when {
                                tempActual < 196 -> estado.text = "Calentando"
                                tempActual in 196..210 -> estado.text = "Tueste Ligero"
                                tempActual > 210 -> {
                                    estado.text = "Finalizado"
                                    tostando = false
                                    boton.text = "INICIAR TOSTADO"
                                }
                            }
                        }

                        "Medio" -> {

                            tipo.text = "Medio"

                            when {
                                tempActual < 210 -> estado.text = "Desarrollo"
                                tempActual in 210..220 -> estado.text = "Tueste Medio"
                                tempActual > 220 -> {
                                    estado.text = "Finalizado"
                                    tostando = false
                                    boton.text = "INICIAR TOSTADO"
                                }
                            }
                        }

                        "Oscuro" -> {

                            tipo.text = "Oscuro"

                            when {
                                tempActual < 225 -> estado.text = "Intensificando"
                                tempActual in 225..235 -> estado.text = "Tueste Oscuro"
                                tempActual > 235 -> {
                                    estado.text = "Finalizado"
                                    tostando = false
                                    boton.text = "INICIAR TOSTADO"
                                }
                            }
                        }
                    }

                    handler.postDelayed(this, 1000)
                }
            }
        }

        // ESTADO INICIAL
        temperatura.text = "120°C"
        estado.text = "Listo"
        tipo.text = "Medio"

        // BOTON LIGERO
        btnLigero.setOnClickListener {

            tipoSeleccionado = "Ligero"

            tipo.text = "Ligero"

            btnLigero.setBackgroundColor(getColor(R.color.coffee_medium))
            btnMedio.setBackgroundColor(getColor(R.color.background))
            btnOscuro.setBackgroundColor(getColor(R.color.background))
        }

        // BOTON MEDIO
        btnMedio.setOnClickListener {

            tipoSeleccionado = "Medio"

            tipo.text = "Medio"

            btnLigero.setBackgroundColor(getColor(R.color.background))
            btnMedio.setBackgroundColor(getColor(R.color.coffee_medium))
            btnOscuro.setBackgroundColor(getColor(R.color.background))
        }

        // BOTON OSCURO
        btnOscuro.setOnClickListener {

            tipoSeleccionado = "Oscuro"

            tipo.text = "Oscuro"

            btnLigero.setBackgroundColor(getColor(R.color.background))
            btnMedio.setBackgroundColor(getColor(R.color.background))
            btnOscuro.setBackgroundColor(getColor(R.color.coffee_medium))
        }

        // BOTON INICIAR / DETENER
        boton.setOnClickListener {

            if (!tostando) {

                tostando = true

                estado.text = "Iniciando"

                boton.text = "DETENER PROCESO"

                handler.post(runnable)

            } else {

                tostando = false

                handler.removeCallbacks(runnable)

                estado.text = "Detenido"

                boton.text = "INICIAR TOSTADO"
            }
        }

        // EDGE TO EDGE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }
}