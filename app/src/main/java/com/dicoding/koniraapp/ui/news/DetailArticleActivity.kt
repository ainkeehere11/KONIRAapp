package com.dicoding.koniraapp.ui.news

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.koniraapp.R
import com.dicoding.koniraapp.databinding.DetailActivityArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: DetailActivityArticleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>("ARTICLE_KEY")

        article?.let {
            binding.articleTitle.text = it.title
            binding.articleDescription.text = it.description
            Glide.with(this).load(it.urlToImage).into(binding.articleImage)
        }
    }
}