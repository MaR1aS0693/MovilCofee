package com.example.movilcofee
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val temperatura = findViewById<TextView>(R.id.txtTemperatura)
        val estado = findViewById<TextView>(R.id.txtEstado)
        val tipo = findViewById<TextView>(R.id.txtTipo)
        val boton = findViewById<Button>(R.id.btnDetener)
        var tempActual = 120
        var tostando = false
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {

            override fun run() {

                if (tempActual <= 235) {

                    tempActual += 2

                    temperatura.text = "$tempActual°C"

                    when {

                        tempActual in 196..210 -> {

                            estado.text = "Primera grieta"
                            tipo.text = "Ligero"

                        }

                        tempActual in 211..220 -> {

                            estado.text = "Desarrollo"
                            tipo.text = "Medio"

                        }

                        tempActual in 221..230 -> {

                            estado.text = "Intensificando"
                            tipo.text = "Oscuro"

                        }

                        tempActual > 230 -> {

                            estado.text = "Finalizado"
                            tipo.text = "Oscuro"

                        }

                        else -> {

                            estado.text = "Calentando"
                            tipo.text = "Preparando"

                        }
                    }

                    handler.postDelayed(this, 1000)
                }
            }
        }

        temperatura.text = "120 c"
        estado.text = "Listo"
        tipo.text = "Esperando"

        boton.setOnClickListener {
            if (!tostando) {

                tostando = true

                boton.text = "DETENER PROCESO"

                handler.post(runnable)

            } else {

                tostando = false

                handler.removeCallbacks(runnable)

                estado.text = "Detenido"

                boton.text = "Iniciar Tostado"
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}