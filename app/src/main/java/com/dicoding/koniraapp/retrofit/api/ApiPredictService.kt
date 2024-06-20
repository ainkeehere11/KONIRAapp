package com.dicoding.koniraapp.retrofit.api

import com.dicoding.koniraapp.retrofit.response.PredictResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiPredictService {
    @Multipart
    @POST("predict")
    suspend fun getPrediction(@Part image: MultipartBody.Part): PredictResponse
}