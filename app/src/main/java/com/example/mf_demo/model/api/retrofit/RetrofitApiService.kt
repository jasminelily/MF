package com.example.mf_demo.model.api.retrofit

import com.example.mf_demo.model.data.entity.User
import com.example.mf_demo.model.data.entity.UserDetail
import com.example.mf_demo.model.data.entity.UserDetailRepo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): ApiResponse<List<User>>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): ApiResponse<UserDetail>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): ApiResponse<List<UserDetailRepo>>
}