package com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api.QuoteService
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.models.QuoteList

class QuoteRepository(private val quoteService: QuoteService) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int){
        val result = quoteService.getQuotes(page)
        if(result?.body() != null){
            quotesLiveData.postValue(result.body())
        }
    }
}