package com.dicoding.koniraapp.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.koniraapp.repository.UserRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _response = MutableLiveData<String?>()
    val response: MutableLiveData<String?> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String, confPass: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val registerResponse = userRepository.register(name, email, password, confPass)
                _response.value = registerResponse.msg.toString()
                Log.d(TAG, registerResponse.msg.toString())
                _isLoading.value = false
            } catch (e: HttpException) {
                val responseBody = e.response()?.errorBody()?.string()
                val errorJson = responseBody?.let { JSONObject(it) }
                val errorMessage =
                    errorJson?.getString("msg")
                _response.value = errorMessage
                if (errorMessage != null) {
                    Log.d(LoginViewModel.TAG, errorMessage)
                }
                _isLoading.value = false
            }
        }
    }

    companion object {
        const val TAG = "SignupViewModel"
    }
}