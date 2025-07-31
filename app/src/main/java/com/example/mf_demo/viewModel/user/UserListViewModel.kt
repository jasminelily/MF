package com.example.mf_demo.viewModel.user

import androidx.lifecycle.viewModelScope
import com.example.mf_demo.module.api.base.ApiResult
import com.example.mf_demo.module.data.User
import com.example.mf_demo.viewModel.base.BaseRequestData
import com.example.mf_demo.viewModel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserListViewModel() : BaseViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    var s = true

    init {
        getData()
    }

    override fun startGetData(param: BaseRequestData?) {
        viewModelScope.launch {

            isLoading = true
            isRetry = false

            api.getUsers().let {
                isLoading = false
                when (it) {
                    is ApiResult.Success -> {
                        _users.value = it.data
                    }

                    is ApiResult.Error -> {
                        super.handleError(it)
                        isRetry = true
                    }
                }
            }
        }
    }
}
