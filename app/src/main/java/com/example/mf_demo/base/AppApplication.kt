package com.example.mf_demo.base

import android.app.Application
import com.example.mf_demo.base.AppCrashHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCrashHandler().apply {
            init(this@AppApplication)
        }
    }
}