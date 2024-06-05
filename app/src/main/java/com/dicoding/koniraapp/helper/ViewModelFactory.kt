package com.dicoding.koniraapp.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.koniraapp.auth.RegisterViewModel
import com.dicoding.koniraapp.pref.UserPreferences
import com.dicoding.koniraapp.repository.NewsRepository
import com.dicoding.koniraapp.repository.UserRepository

class ViewModelFactory(
    private val repository: UserRepository,
    private val newsRepository: NewsRepository,
    private val pref: UserPreferences,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository, theme) as T
            }
            modelClass.isAssignableFrom(ListBeritaViewModel::class.java) -> {
                ListBeritaViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val userRepository = Inject.provideRepository(context)
                    val newsRepository = Inject.provideNewsRepository(context)
                    val userPreferences = Inject.providePreferences(context)
//                    val themePreference = Inject.provideTheme(context)
//                    INSTANCE = ViewModelFactory(userRepository, newsRepository, userPreference, themePreference)
                }
            }
            return INSTANCE ?: throw IllegalStateException("ViewModelFactory should not be null")
        }
    }
}