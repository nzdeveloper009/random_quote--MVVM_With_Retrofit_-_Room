package com.nzdeveloper009.randomquote_mvvmwithretrofitroom

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.api.RetrofitConfig
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.db.QuoteDatabase
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.repository.QuoteRepository
import com.nzdeveloper009.randomquote_mvvmwithretrofitroom.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quoteRepository: QuoteRepository

    override fun onCreate() {
        super.onCreate()
        initailize()
        setupWorker()
    }

    private fun setupWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest = PeriodicWorkRequest.Builder(QuoteWorker::class.java,10,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initailize() {
        val quoteService = RetrofitConfig.quoteService
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService, database,applicationContext)

    }
}