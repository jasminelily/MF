package com.example.mf_demo.module.api.base

import com.example.mf_demo.module.data.User
import com.example.mf_demo.module.data.UserDetail
import com.example.mf_demo.module.data.UserDetailRepo

interface IApiRepository {
    suspend fun getUsers(): ApiResult<List<User>>
    suspend fun getUserDetail(username: String): ApiResult<UserDetail>
    suspend fun getUserRepos(username: String): ApiResult<List<UserDetailRepo>>
}