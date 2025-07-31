package com.example.mf_demo.model.api.base

import com.example.mf_demo.model.api.retrofit.RetrofitRepository

object ApiModule {
    fun provideRepository(): IApiRepository {
        //if want to switch to other repository,
        //create new repo extends IApiRepository, and change it here
        return RetrofitRepository()
    }
}