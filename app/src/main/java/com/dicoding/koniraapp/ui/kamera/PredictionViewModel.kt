package com.dicoding.koniraapp.ui.kamera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.koniraapp.repo.PredictionRepository
import com.dicoding.koniraapp.retrofit.response.PredictResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PredictionViewModel(private val predictionRepository: PredictionRepository) : ViewModel() {
    fun getPrediction(imagePart: MultipartBody.Part, onSuccess: (PredictResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "imagefile: $imagePart")
                val response = predictionRepository.getPrediction(imagePart)
                onSuccess(response)
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }

    companion object{
        const val  TAG = "PredictionViewModel"
    }
}