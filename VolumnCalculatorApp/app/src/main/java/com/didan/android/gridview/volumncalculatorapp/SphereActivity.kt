package com.didan.android.gridview.volumncalculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SphereActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sphere)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView: TextView = findViewById(R.id.textView2)
        val result = findViewById<TextView>(R.id.textView3)
        val button = findViewById<Button>(R.id.btn1)
        val editText = findViewById<EditText>(R.id.editText_Sphere)

        button.setOnClickListener {
            val radius = editText.text.toString()
            val r = radius.toInt()

            val volume = (4.0/3.0) * Math.PI * r * r * r
            result.text = "V = $volume unit³"
        }
    }
}