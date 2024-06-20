package com.dicoding.koniraapp.ui.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.koniraapp.databinding.ActivityAllArticlesBinding
import com.dicoding.koniraapp.ui.adapter.ArticleAdapter

class AllArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articles = JsonUtils.loadArticles(this)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = ArticleAdapter(this, articles) { article ->
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("ARTICLE_KEY", article)
            startActivity(intent)
        }
    }
}