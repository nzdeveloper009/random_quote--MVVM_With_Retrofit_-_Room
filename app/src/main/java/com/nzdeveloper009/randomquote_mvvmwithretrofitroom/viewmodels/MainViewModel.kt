package com.nzdeveloper009.randomquote_mvvmwithretrofitroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.models.QuoteList
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    val quotes: LiveData<QuoteList>
        get() = repository.quotes
}