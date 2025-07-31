package com.example.mf_demo.viewModel.base


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.mf_demo.module.api.base.ApiModule
import com.example.mf_demo.module.api.base.ApiResult
import com.example.mf_demo.ui.components.dialog.DialogUI
import com.example.mf_demo.util.constant.CType

abstract class BaseViewModel : ViewModel() {
    var isLoading by mutableStateOf(true)
    var isShowDialog by mutableStateOf(false)
    var isRetry by mutableStateOf(false)

    //var isRefresh by mutableStateOf(false)

    val api = ApiModule.provideRepository()

    fun getData(param: BaseRequestData?) {
        startGetData(param)
    }

    fun handleError(error: ApiResult.Error) {

    }

    @Composable
    fun ShowRetry() {
        isShowDialog = isRetry
        if (!isRetry) {
            return
        }
        ShowDialog(CType.MessageType.Retry) { startGetData(null) }
    }

    @Composable
    fun ShowDialog(
        type: CType.MessageType,
        onClick: () -> Unit
    ) {
        if (!isShowDialog) {
            return
        }
        DialogUI(
            LocalContext.current,
            type,
            onDismiss = {
                isShowDialog = false
            },
            onConfirmation = {
                isShowDialog = false
                onClick()
            }
        )
    }

    abstract fun startGetData(param: BaseRequestData?)
}

