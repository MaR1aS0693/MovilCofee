package com.example.movilcofee

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GraficasActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart

    private val entries = ArrayList<Entry>()

    private var tiempo = 0f
    private var temperatura = 30f

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grafica)

        lineChart = findViewById(R.id.lineChart)

        iniciarGrafica()
    }

    private fun iniciarGrafica() {

        val runnable = object : Runnable {

            override fun run() {

                // SIMULACION DEL TOSTADO

                if (temperatura < 230f) {

                    temperatura += (2..5).random()

                } else {

                    temperatura -= 1
                }

                // AGREGAR PUNTO

                entries.add(Entry(tiempo, temperatura))

                val dataSet = LineDataSet(entries, "Temperatura")

                val lineData = LineData(dataSet)

                lineChart.data = lineData

                lineChart.invalidate()

                tiempo += 1f

                handler.postDelayed(this, 1000)
            }
        }

        handler.post(runnable)
    }
}