package com.example.mf_demo.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mf_demo.ui.screen.base.CrashScreen
import com.example.mf_demo.util.helper.OpenHelper

class CrashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrashScreen(onRestart = {
                OpenHelper.restartApp(context = this)
                finish()
            })
        }
    }

}