package com.example.movilcofee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class GraficasActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart

    private val entries = ArrayList<Entry>()

    private var tiempo = 0f
    private var temperatura = 90f

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // IMPORTANTE:
        // ESTA ACTIVITY USA activity_grafica
        setContentView(R.layout.activity_grafica)

        // GRAFICA
        lineChart = findViewById(R.id.lineChart)

        iniciarGrafica()

        // BOTTOM NAVIGATION
        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // MARCAR GRAFICAS COMO ACTIVO
        bottomNavigation.selectedItemId = R.id.nav_grafica

        // NAVEGACION ENTRE PANTALLAS
        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                // INICIO
                R.id.nav_inicio -> {

                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )

                    finish()

                    true
                }

                // GRAFICAS
                R.id.nav_grafica -> {

                    true
                }

                // EVENTOS
                R.id.nav_eventos -> {

                    startActivity(
                        Intent(this, EventosActivity::class.java)
                    )

                    finish()

                    true
                }

                // AJUSTES
                R.id.nav_ajustes -> {

                    startActivity(
                        Intent(this, AjustesActivity::class.java)
                    )

                    finish()

                    true
                }

                else -> false
            }
        }

        // EDGE TO EDGE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainGraficas)) { v, insets ->

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

    // FUNCION PARA LA GRAFICA
    private fun iniciarGrafica() {

        val runnable = object : Runnable {

            override fun run() {

                // SIMULACION DEL TOSTADO

                if (temperatura < 220f) {

                    temperatura += (2..5).random()

                } else {

                    temperatura = 220f
                }

                // AGREGAR PUNTOS
                entries.add(Entry(tiempo, temperatura))

                // DATASET
                val dataSet = LineDataSet(entries, "Temperatura °C")

                // DATA
                val lineData = LineData(dataSet)

                // ASIGNAR
                lineChart.data = lineData

                // ACTUALIZAR
                lineChart.invalidate()

                // AUMENTAR TIEMPO
                tiempo += 1f

                // REPETIR
                lineChart.postDelayed(this, 1000)
            }
        }

        lineChart.post(runnable)
    }
}