package com.example.mf_demo.base

import android.app.Application

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCrashHandler().apply {
            init(this@AppApplication)
        }
    }
}