package com.example.mf_demo.util.helper

import android.content.Context
import android.widget.Toast
import com.example.mf_demo.R
import com.example.mf_demo.util.constant.CType

object DialogHelper {

    data class DialogData(
        val title: String,
        val message: String,
        val okBtn: String, val cancelBtn: String
    )

//    fun showToast(
//        context: Context,
//        type: CType.MessageType,
//        duration: Int = Toast.LENGTH_SHORT
//    ) {
//        val msg = getTexts(context, type).message
//        Toast.makeText(context, msg, duration).show()
//    }

    fun getTexts(
        context: Context,
        type: CType.MessageType,
    ): DialogData {
        var title = ""
        var message = ""
        var okBtn = context.getString(R.string.ui_dialog_btn_ok)
        var cancelBtn = context.getString(R.string.ui_dialog_btn_cancel)

        when (type) {
            CType.MessageType.OpenUrl -> {
                title = context.getString(R.string.ui_dialog_title_open_browser)
                message = context.getString(R.string.ui_dialog_msg_open_browser)
            }

            CType.MessageType.Retry -> {
                title = context.getString(R.string.ui_dialog_title_retry)
                okBtn = context.getString(R.string.ui_dialog_btn_retry)
            }

            CType.MessageType.Setting -> {
                message = context.getString(R.string.desc_btn_setting)
            }
        }
        return DialogData(title, message, okBtn, cancelBtn)
    }

}