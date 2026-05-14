package com.example.movilcofee

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider

class VelocidadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_velocidad)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainVelocidad)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // RPM

        val sliderRPM = findViewById<Slider>(R.id.sliderRPM)

        val txtRPM = findViewById<TextView>(R.id.txtRPM)

        sliderRPM.addOnChangeListener { _, value, _ ->

            txtRPM.text = "${value.toInt()} RPM"
        }
    }
}