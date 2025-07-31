package com.example.mf_demo.module.api.base

import com.example.mf_demo.module.data.entity.User
import com.example.mf_demo.module.data.entity.UserDetail
import com.example.mf_demo.module.data.entity.UserDetailRepo

interface IApiRepository {
    suspend fun getUsers(page: Int = 1, pageSize: Int = 30): ApiResult<List<User>>
    suspend fun getUserDetail(username: String): ApiResult<UserDetail>
    suspend fun getUserRepos(username: String): ApiResult<List<UserDetailRepo>>
}