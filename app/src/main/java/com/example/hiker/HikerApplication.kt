package com.example.hiker

import android.app.Application
import com.example.hiker.model.HikeDao
import com.example.hiker.model.HikeDatabase
import com.example.hiker.model.HikeRepository

class HikerApplication : Application() {

    companion object {

        private var applicationInstance:HikerApplication? = null

        private var repositoryInstance:HikeRepository? = null

        fun getRepositoryInstance():HikeRepository? {
            return repositoryInstance
        }

    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
        val hikeDao : HikeDao = HikeDatabase.getInstance(applicationContext).hikerDao()
        repositoryInstance = HikeRepository(hikeDao)
    }













}