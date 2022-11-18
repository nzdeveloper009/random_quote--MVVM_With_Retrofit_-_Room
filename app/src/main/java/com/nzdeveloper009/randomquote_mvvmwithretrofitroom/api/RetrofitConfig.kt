package com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    private const val BASE_URL = "https://quotable.io/"

    private val INSTANCE: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val quoteService : QuoteService by lazy {
        INSTANCE.create(QuoteService::class.java)
    }

}