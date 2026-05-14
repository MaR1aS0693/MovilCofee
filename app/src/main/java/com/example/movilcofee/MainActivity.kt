package com.example.movilcofee

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // VARIABLES GENERALES
        var tostando = false
        var perfilSeleccionado = "Medio"

        // VARIABLES DEL PERFIL
        var temperaturaObjetivo = 220
        var tiempoObjetivo = 14
        var rpmObjetivo = 45

        // VARIABLES DEL PROCESO
        var tiempoActual = 0
        var tempActual = 240

        val handler = Handler(Looper.getMainLooper())

        // FUNCION PARA CONFIGURAR PERFILES
        fun configurarPerfil(perfil: String) {

            perfilSeleccionado = perfil

            when (perfil) {

                "Ligero" -> {

                    temperaturaObjetivo = 210
                    tiempoObjetivo = 12
                    rpmObjetivo = 40

                    btnLigero.setBackgroundColor(getColor(R.color.coffee_medium))
                    btnMedio.setBackgroundColor(getColor(R.color.background))
                    btnOscuro.setBackgroundColor(getColor(R.color.background))
                }

                "Medio" -> {

                    temperaturaObjetivo = 220
                    tiempoObjetivo = 14
                    rpmObjetivo = 45

                    btnLigero.setBackgroundColor(getColor(R.color.background))
                    btnMedio.setBackgroundColor(getColor(R.color.coffee_medium))
                    btnOscuro.setBackgroundColor(getColor(R.color.background))
                }

                "Oscuro" -> {

                    temperaturaObjetivo = 230
                    tiempoObjetivo = 18
                    rpmObjetivo = 50

                    btnLigero.setBackgroundColor(getColor(R.color.background))
                    btnMedio.setBackgroundColor(getColor(R.color.background))
                    btnOscuro.setBackgroundColor(getColor(R.color.coffee_medium))
                }
            }

            tipo.text = perfilSeleccionado
        }

        // RUNNABLE PRINCIPAL
        val runnable = object : Runnable {

            override fun run() {

                if (tostando) {

                    tiempoActual++

                    // SIMULACION TERMICA
                    when {

                        tiempoActual <= 1 -> {

                            tempActual = 90
                            estado.text = "Carga de granos"
                        }

                        tiempoActual in 2..3 -> {

                            tempActual += 20
                            estado.text = "Secado"
                        }

                        tiempoActual in 4..6 -> {

                            tempActual += 10
                            estado.text = "Calentamiento"
                        }

                        tiempoActual in 7..10 -> {

                            tempActual += 5
                            estado.text = "Primer crack"
                        }

                        tiempoActual in 11..tiempoObjetivo -> {

                            tempActual += 3
                            estado.text = "Desarrollo del tostado"
                        }
                    }

                    // LIMITE DE TEMPERATURA
                    if (tempActual >= temperaturaObjetivo) {

                        tempActual = temperaturaObjetivo

                        estado.text = "Perfil completado"

                        tostando = false

                        boton.text = "INICIAR TOSTADO"
                    }

                    // ACTUALIZAR TEMPERATURA
                    temperatura.text = "$tempActual°C"

                    // CONTINUAR PROCESO
                    if (tostando) {

                        handler.postDelayed(this, 1000)
                    }
                }
            }
        }

        // ESTADO INICIAL
        temperatura.text = "240°C"
        estado.text = "Precalentamiento"
        tipo.text = "Medio"

        // PERFIL INICIAL
        configurarPerfil("Medio")

        // BOTONES DE PERFIL
        btnLigero.setOnClickListener {

            configurarPerfil("Ligero")
        }

        btnMedio.setOnClickListener {

            configurarPerfil("Medio")
        }

        btnOscuro.setOnClickListener {

            configurarPerfil("Oscuro")
        }

        // BOTON INICIAR / DETENER
        boton.setOnClickListener {

            if (!tostando) {

                tostando = true

                tiempoActual = 0

                tempActual = 240

                estado.text = "Precalentamiento"

                boton.text = "DETENER PROCESO"

                handler.post(runnable)

            } else {

                tostando = false

                handler.removeCallbacks(runnable)

                estado.text = "Proceso detenido"

                boton.text = "INICIAR TOSTADO"
            }
        }

        // BOTTOM NAVIGATION
        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.selectedItemId = R.id.nav_inicio

        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_inicio -> true

                R.id.nav_grafica -> {

                    startActivity(
                        Intent(this, GraficasActivity::class.java)
                    )

                    true
                }

                R.id.nav_tiempo -> {

                    startActivity(
                        Intent(this, TiempoActivity::class.java)
                    )

                    true
                }

                R.id.nav_eventos -> {

                    startActivity(
                        Intent(this, EventosActivity::class.java)
                    )

                    true
                }

                R.id.nav_ajustes -> {

                    startActivity(
                        Intent(this, AjustesActivity::class.java)
                    )

                    true
                }

                else -> false
            }
        }

        // EDGE TO EDGE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

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