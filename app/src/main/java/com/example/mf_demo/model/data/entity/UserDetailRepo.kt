package com.example.mf_demo.model.data.entity

data class UserDetailRepo(
    val name: String,
    val language: String?,
    val stargazersCount: Int,
    val description: String?,
    val htmlUrl: String,
    val fork: Boolean
)