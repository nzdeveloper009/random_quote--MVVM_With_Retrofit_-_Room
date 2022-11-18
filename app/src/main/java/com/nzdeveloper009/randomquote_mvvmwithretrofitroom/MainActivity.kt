package com.nzdeveloper009.randomquote_mvvmwithretrofitroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api.RetrofitConfig
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository.QuoteRepository
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.viewmodels.MainViewModel
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // here we can use dependency injection for QuoteServices
        val quoteService = RetrofitConfig.quoteService
        val repository = QuoteRepository(quoteService)
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository))[MainViewModel::class.java]

        mainViewModel.quotes.observe(this, Observer {
            Log.d("NZDEVELOPER", "onCreate: ${it.results.toString()}")
        })
    }
}