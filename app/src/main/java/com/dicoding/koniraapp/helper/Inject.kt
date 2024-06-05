package com.dicoding.koniraapp.helper

import android.content.Context
import com.dicoding.koniraapp.pref.UserPreferences
import com.dicoding.koniraapp.pref.userDataStore
import com.dicoding.koniraapp.repository.NewsRepository
import com.dicoding.koniraapp.repository.UserRepository
import com.dicoding.koniraapp.retrofit.api.ApiConfig
import com.dicoding.koniraapp.retrofit.api.NewsApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Inject {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.userDataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val apiPredictService = ApiPredictConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService, apiPredictService)
    }

    fun provideNewsRepository(context: Context): NewsRepository {
        val newsApiService = NewsApiConfig.getApiService()
        return NewsRepository.getInstance(newsApiService)
    }

    fun providePreferences(context: Context): UserPreferences {
        return UserPreferences.getInstance(context.userDataStore)
    }
}