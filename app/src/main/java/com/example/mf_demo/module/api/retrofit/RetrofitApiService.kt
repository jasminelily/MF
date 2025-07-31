package com.example.mf_demo.module.api.retrofit

import com.example.mf_demo.module.data.User
import com.example.mf_demo.module.data.UserDetail
import com.example.mf_demo.module.data.UserDetailRepo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiService {
    @GET("users")
    suspend fun getUsers(): ApiResponse<List<User>>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): ApiResponse<UserDetail>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): ApiResponse<List<UserDetailRepo>>
}