package com.example.mf_demo.util.helper.log

import android.os.Bundle
import com.example.mf_demo.util.constant.CType

object LogHelper {
    fun log(log: DLog) {
        when (log.logType) {
            CType.LogType.Action -> {
                logAction(log)
            }

            CType.LogType.Screen -> {
                logScreen(log)
            }

            CType.LogType.Error -> {
                logError(log)
            }

            CType.LogType.Api -> {
                logApi(log)
            }
        }
    }

    private fun logAction(log: DLog) {

    }

    private fun logScreen(log: DLog) {
        getScreen(log).let {
            //send to server, like use firebase
        }
    }

    private fun logError(log: DLog) {
    }

    private fun logApi(log: DLog) {

    }

    private fun getScreen(log: DLog): Bundle {
        return when (log.screenType) {
            CType.ScreenType.UserList -> {
                //set log format data
                Bundle()
            }

            CType.ScreenType.UserDetail -> {
                Bundle()
            }

            CType.ScreenType.None -> {
                Bundle()
            }
        }

    }
}