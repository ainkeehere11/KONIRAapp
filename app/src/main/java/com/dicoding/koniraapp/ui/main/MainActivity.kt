package com.dicoding.koniraapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.koniraapp.R
import com.dicoding.koniraapp.ui.kamera.ActivityScan
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Handle home action
                    true
                }
                R.id.nav_scan -> {
                    // Handle scan action
                    val intent = Intent(this, ActivityScan::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    // Handle profile action
                    true
                }
                R.id.map -> {
                    // Handle map action
                    true
                }
                else -> false
            }
        }
    }
}