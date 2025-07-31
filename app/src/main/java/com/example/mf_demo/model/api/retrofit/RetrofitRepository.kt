package com.example.mf_demo.model.api.retrofit

import com.example.mf_demo.model.api.base.IApiRepository
import com.example.mf_demo.model.api.base.ApiResult
import com.example.mf_demo.model.data.entity.User
import com.example.mf_demo.model.data.entity.UserDetail
import com.example.mf_demo.model.data.entity.UserDetailRepo
import com.example.mf_demo.model.network.retrofit.RetrofitSetting

class RetrofitRepository : IApiRepository {
    private val api = RetrofitSetting.apiRetrofit

    override suspend fun getUsers(page: Int, pageSize: Int): ApiResult<List<User>> {
        val since = (page - 1) * pageSize
        return api.getUsers(since = since, perPage = pageSize).toApiResult()
    }

    override suspend fun getUserDetail(username: String): ApiResult<UserDetail> {
        return api.getUserDetail(username).toApiResult()
    }

    override suspend fun getUserRepos(username: String): ApiResult<List<UserDetailRepo>> {
        return api.getUserRepos(username).toApiResult()
    }

}