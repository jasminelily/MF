package com.example.mf_demo.module.data.entity


data class UserDetail(
    val login: String,
    val avatarUrl: String,
    val name: String,
    val followers: Int,
    val following: Int
)