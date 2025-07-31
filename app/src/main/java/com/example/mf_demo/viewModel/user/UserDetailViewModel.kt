package com.example.mf_demo.viewModel.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.mf_demo.model.api.base.ApiResult
import com.example.mf_demo.model.data.entity.UserDetail
import com.example.mf_demo.model.data.entity.UserDetailRepo
import com.example.mf_demo.viewModel.base.BaseRequestData
import com.example.mf_demo.viewModel.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserDetailViewModel() : BaseViewModel() {

    var userDetail by mutableStateOf<UserDetail?>(null)
    var userRepos by mutableStateOf<List<UserDetailRepo>>(emptyList())

    var selectedRepo by mutableStateOf<UserDetailRepo?>(null)

    override fun startGetData(param: BaseRequestData?) {
        param?.let {
            loadUserDetail(it.key)
        }
    }


    private fun loadUserDetail(username: String) {
        viewModelScope.launch {
            isLoading = true

            val detailDeferred = async { api.getUserDetail(username) }
            val reposDeferred = async { api.getUserRepos(username) }

            detailDeferred.await().let {
                isLoading = false
                when (it) {
                    is ApiResult.Success -> {
                        userDetail = it.data
                    }

                    is ApiResult.Error -> {
                        super.handleError(it)
                    }
                }
            }

            reposDeferred.await().let {
                isLoading = false
                when (it) {
                    is ApiResult.Success -> {
                        userRepos = it.data
                    }

                    is ApiResult.Error -> {
                        super.handleError(it)
                    }
                }
            }
        }
    }
}