package com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConfig {

    private const val BASE_URL = "https://quotable.io/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES) // write timeout
        .readTimeout(2, TimeUnit.MINUTES) // read timeout
        .build()

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