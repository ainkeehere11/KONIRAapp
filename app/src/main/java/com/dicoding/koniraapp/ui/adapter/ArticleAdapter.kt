package com.dicoding.koniraapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.koniraapp.databinding.ItemRowNewsBinding
import com.dicoding.koniraapp.databinding.ItemRowhomenewsBinding
import com.dicoding.koniraapp.ui.news.Article
import com.dicoding.koniraapp.ui.news.AllArticleActivity
import com.dicoding.koniraapp.ui.news.DetailArticleActivity

class ArticleAdapter(
    private val context: Context,
    private val articles: List<Article>,
    private val listener: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemRowNewsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, listener)
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(private val binding: ItemRowNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, listener: (Article) -> Unit) {
            binding.tvTitle.text = article.title
            Glide.with(binding.root).load(article.urlToImage).into(binding.imgNews)
            binding.root.setOnClickListener { listener(article) }
        }
    }
}