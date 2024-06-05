package com.dicoding.koniraapp.repository

import androidx.lifecycle.liveData
import com.dicoding.koniraapp.model.UserLogin
import com.dicoding.koniraapp.model.UserRegis
import com.dicoding.koniraapp.pref.UserModel
import com.dicoding.koniraapp.pref.UserPreferences
import com.dicoding.koniraapp.response.LoginResponse
import com.dicoding.koniraapp.response.RegisterResponse
import com.dicoding.koniraapp.retrofit.api.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreferences,
    private val apiService: ApiService,
    private val apiPredictService: ApiPredictService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        confPass: String
    ): RegisterResponse {
        val userRegister = UserRegis(name, email, password, confPass)
        return apiService.register(userRegister)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val userLogin = UserLogin(email, password)
        return apiService.login(userLogin)
    }

    fun uploadImage(imageFile: File) = liveData {
        emit(Result.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )

        try {
            val successResponse = apiPredictService.uploadImage(multipartBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {

        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreferences,
            apiService: ApiService,
            apiPredictService: ApiPredictService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService, apiPredictService)
            }.also { instance = it }
    }
}