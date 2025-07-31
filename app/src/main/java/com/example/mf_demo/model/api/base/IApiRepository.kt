package com.example.mf_demo.model.api.base

import com.example.mf_demo.model.data.entity.User
import com.example.mf_demo.model.data.entity.UserDetail
import com.example.mf_demo.model.data.entity.UserDetailRepo

interface IApiRepository {
    suspend fun getUsers(page: Int, pageSize: Int): ApiResult<List<User>>
    suspend fun getUserDetail(username: String): ApiResult<UserDetail>
    suspend fun getUserRepos(username: String): ApiResult<List<UserDetailRepo>>
}