package com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api.QuoteService
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.db.QuoteDatabase
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.models.QuoteList
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {


    // interact with API
    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()

    val quotes: LiveData<Response<QuoteList>>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {

                val result = quoteService.getQuotes(page)
                if (result?.body() != null) {
                    // add data to database room
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    // set data to variable to show on list
                    quotesLiveData.postValue(Response.Success(result.body()))
                } else {
                    quotesLiveData.postValue(Response.Error("API Error"))

                }
            } catch (e: Exception) {
                quotesLiveData.postValue(Response.Error(e.message.toString()))
            }
        } else {
            try {
                // interact with Database when network is not available
                val quotes = quoteDatabase.quoteDao().getQuotes()
                val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
                // set data to variable to show on list
                quotesLiveData.postValue(Response.Success(quoteList))
            } catch (e: Exception){
                quotesLiveData.postValue(Response.Error(e.message.toString()))
            }
        }

    }

    // call this function through work manager class

    suspend fun getQuotesBackground() {
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body() != null) {
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }


}