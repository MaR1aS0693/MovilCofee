package com.example.movilcofee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class TiempoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_tiempo)

        // AJUSTE DE BORDES
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainTiempo)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // BARRA DE NAVEGACION
        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // MARCAR GRAFICAS/TIEMPO COMO ACTIVO
        bottomNavigation.selectedItemId = R.id.nav_grafica

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

                    startActivity(
                        Intent(this, GraficasActivity::class.java)
                    )

                    finish()

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
    }
}