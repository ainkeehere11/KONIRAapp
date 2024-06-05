package com.dicoding.koniraapp.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.koniraapp.pref.UserModel
import com.dicoding.koniraapp.repository.UserRepository
import com.dicoding.koniraapp.response.LoginResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _response = MutableLiveData<String?>()
    val response: MutableLiveData<String?> = _response

    private val _token = MutableLiveData<LoginResponse>()
    val token: LiveData<LoginResponse> = _token

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val loginResponse = userRepository.login(email, password)
                _token.postValue(loginResponse)
                Log.d(TAG, loginResponse.message.toString())
                _isLoading.value = false
            } catch (e: HttpException) {
                val responseBody = e.response()?.errorBody()?.string()
                val errorJson = responseBody?.let { JSONObject(it) }
                val errorMessage =
                    errorJson?.getString("msg")
                _response.value = errorMessage
                if (errorMessage != null) {
                    Log.d(TAG, errorMessage)
                }
                _isLoading.value = false
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}