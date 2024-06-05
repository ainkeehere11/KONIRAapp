package com.dicoding.koniraapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.koniraapp.pref.UserModel
import com.dicoding.koniraapp.repository.UserRepository

class MainViewModel(private val userRepository: UserRepository, val pref: ThemePreferences): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}