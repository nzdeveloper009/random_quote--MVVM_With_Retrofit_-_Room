package com.nzdeveloper009.randomquote_mvvmwithretrofitroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api.RetrofitConfig
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository.QuoteRepository
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository.Response
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.viewmodels.MainViewModel
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository))[MainViewModel::class.java]

        mainViewModel.quotes.observe(this, Observer {
            when(it){
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        Toast.makeText(this@MainActivity,it.results.size.toString(),Toast.LENGTH_LONG).show()
                    }
                }
                is Response.Error -> {
//                    Toast.makeText(this@MainActivity,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    Toast.makeText(this@MainActivity,"Some Error Occured",Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}