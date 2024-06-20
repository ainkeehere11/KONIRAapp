package com.dicoding.koniraapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.koniraapp.R
import com.dicoding.koniraapp.databinding.ActivityMainBinding
import com.dicoding.koniraapp.ui.adapter.HomeArticleAdapter
import com.dicoding.koniraapp.ui.kamera.ActivityScan
import com.dicoding.koniraapp.ui.news.AllArticleActivity
import com.dicoding.koniraapp.ui.news.DetailArticleActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load articles
        val articles = JsonUtils.loadArticles(this)

        // Set up RecyclerView
        binding.rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMain.adapter = HomeArticleAdapter(this, articles.take(5)) { article ->
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("ARTICLE_KEY", article)
            startActivity(intent)
        }

        // Handle "Lihat Semua" click
        binding.tvLihatSemua.setOnClickListener {
            val intent = Intent(this, AllArticleActivity::class.java)
            startActivity(intent)
        }

        // Set up BottomNavigationView
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