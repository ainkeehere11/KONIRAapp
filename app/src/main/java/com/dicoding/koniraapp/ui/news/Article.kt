package com.dicoding.koniraapp.ui.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Article(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
) : Parcelable
