package com.didan.android.planetsapps

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1 - The View: ListView
        val listView: ListView = findViewById(R.id.listView)

        // 2 - The Data Source: List of Planets
        val planet1 = Planet("Mercury", "0 Moons", R.drawable.mercury)
        val planet2 = Planet("Venus", "0 Moons", R.drawable.venus)
        val planet3 = Planet("Earth", "1 Moon", R.drawable.earth)
        val planet4 = Planet("Mars", "2 Moons", R.drawable.mars)
        val planet5 = Planet("Jupiter", "79 Moons", R.drawable.jupiter)
        val planet6 = Planet("Saturn", "83 Moons", R.drawable.saturn)
        val planet7 = Planet("Uranus", "27 Moons", R.drawable.uranus)
        val planet8 = Planet("Neptune", "14 Moons", R.drawable.neptune)

        var planetsList = ArrayList<Planet>()
        planetsList.add(planet1)
        planetsList.add(planet2)
        planetsList.add(planet3)
        planetsList.add(planet4)
        planetsList.add(planet5)
        planetsList.add(planet6)
        planetsList.add(planet7)
        planetsList.add(planet8)

        // 3 - The Adapter: MyCustomAdapter
        val adapter = MyCustomAdapter(this, planetsList)

        // 4 - Connecting the Adapter to the ListView
        listView.adapter = adapter
    }
}