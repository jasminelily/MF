package com.example.mf_demo.ui.components.dialog

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.mf_demo.util.constant.CType
import com.example.mf_demo.util.helper.DialogHelper


@Composable
fun DialogUI(
    context: Context,
    type: CType.MessageType,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val txt = getTexts(context, type)
    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.92f),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            if (txt.okBtn.isNotBlank()) {
                TextButton(onClick = {
                    onConfirmation()
                }) {
                    Text(text = txt.okBtn)
                }
            }
        },
        dismissButton = {
            if (txt.cancelBtn.isNotBlank()) {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = txt.cancelBtn)
                }
            }
        },
        title = {
            Text(text = txt.title, fontSize = 18.sp)
        },
        text = {
            Text(text = txt.message)
        })

}

@Composable
fun getTexts(
    context: Context,
    type: CType.MessageType,
): DialogHelper.DialogData {
    return DialogHelper.getTexts(context, type)
}


