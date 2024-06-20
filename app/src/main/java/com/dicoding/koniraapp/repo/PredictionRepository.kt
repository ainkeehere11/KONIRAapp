package com.dicoding.koniraapp.repo

import android.util.Log
import com.dicoding.koniraapp.retrofit.api.ApiPredictConfig
import com.dicoding.koniraapp.retrofit.api.ApiPredictService
import com.dicoding.koniraapp.retrofit.response.PredictResponse
import okhttp3.MultipartBody

class PredictionRepository(private val apiService: ApiPredictService) {
    suspend fun getPrediction(image: MultipartBody.Part): PredictResponse {
        Log.d("PredictionRepository", "image: $image")
        val apiService = ApiPredictConfig.getApiService()
        return apiService.getPrediction(image)
    }
}