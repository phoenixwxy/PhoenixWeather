package com.phoenix.phoenixweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class PhoenixWeatherApplication : Application() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // TODO need caiyunapp's token
        const val TOKEN = "F77eHAJK8O36HkxC"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}