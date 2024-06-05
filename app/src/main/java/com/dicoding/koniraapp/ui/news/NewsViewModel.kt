package com.dicoding.koniraapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _news = MutableLiveData<List<//isi//>>()
    val news: MutableLiveData<List<//isi//>> = _news

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)

                val result = repository.getNews()
                withContext(Dispatchers.Main) {
                    _news.value = result
                }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}