package com.example.mf_demo.util.constant

object CPath {
    const val BASE_API_URL = "https://api.github.com/"

    enum class APIHeaderType(val type: String) {
        Auth("Authorization")
    }

    //TODO
    //暗号化　or Token, Keyはローカルで保存しない、サーバーから取る
    const val API_AUTH =
        "github_pat_11AAXGNXXXXXXXXXXX"
}