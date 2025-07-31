package com.example.mf_demo.util.helper

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.example.mf_demo.MainActivity
import com.example.mf_demo.ui.activity.CrashActivity

object OpenHelper {

    fun openURL(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    fun startCrashPage(context: Context?) {
        val intent = Intent(context, CrashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context?.startActivity(intent)
    }

    fun restartApp(context: Context?) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context?.startActivity(intent)
    }
}