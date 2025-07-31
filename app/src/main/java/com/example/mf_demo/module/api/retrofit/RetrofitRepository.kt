package com.example.mf_demo.module.api.retrofit

import com.example.mf_demo.module.api.base.IApiRepository
import com.example.mf_demo.module.api.base.ApiResult
import com.example.mf_demo.module.data.User
import com.example.mf_demo.module.data.UserDetail
import com.example.mf_demo.module.data.UserDetailRepo
import com.example.mf_demo.module.network.retrofit.RetrofitSetting

class RetrofitRepository : IApiRepository {
    private val api = RetrofitSetting.apiRetrofit

    override suspend fun getUsers(): ApiResult<List<User>> {
        return api.getUsers().toApiResult()
    }

    override suspend fun getUserDetail(username: String): ApiResult<UserDetail> {
        return api.getUserDetail(username).toApiResult()
    }

    override suspend fun getUserRepos(username: String): ApiResult<List<UserDetailRepo>> {
        return api.getUserRepos(username).toApiResult()
    }

}