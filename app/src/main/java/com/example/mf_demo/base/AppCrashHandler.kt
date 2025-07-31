package com.example.mf_demo.base

import android.content.Context
import android.os.Process
import com.example.mf_demo.util.constant.CType
import com.example.mf_demo.util.helper.OpenHelper
import com.example.mf_demo.util.helper.log.DLog
import com.example.mf_demo.util.helper.log.LogHelper
import java.io.FileNotFoundException
import java.io.IOException
import java.util.concurrent.Executors
import kotlin.system.exitProcess

class AppCrashHandler : Thread.UncaughtExceptionHandler {
    private var context: Context? = null

    fun init(context: Context?) {
        this.context = context
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Executors.newSingleThreadExecutor().execute {
            processLog()
            processError(t, e)
        }

        //killProcess()
    }

    private fun processError(t: Thread, e: Throwable) {

        //or depend on the exception type(or custom exception) to show error
        //EX: IOException,FileNotFoundException
        when (e) {
            is FileNotFoundException -> {
            }

            is IOException -> {
            }

            else -> {
                //show custom message
                //**want to show other error display, like good design page, change here
                // when uncaughtException happened,
                // android dispatch events are not responsible,
                // need to start new thread to show message
//                Looper.prepare()
//                DialogHelper.showToast(context, CType.MessageType.AppCrash)
//                Looper.loop()
                OpenHelper.startCrashPage(context)
            }
        }
    }

    private fun processLog() {
        //Log error
        LogHelper.log(DLog(CType.LogType.Error))
    }

    private fun killProcess() {
        // Kill current process
        Process.killProcess(Process.myPid())
        exitProcess(1)
    }

}