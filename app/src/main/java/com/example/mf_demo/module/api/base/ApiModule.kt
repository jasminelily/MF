package com.example.mf_demo.module.api.base

import com.example.mf_demo.module.api.retrofit.RetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideRepository(): IApiRepository {
        //if want to switch to other repository,
        //create new repo extends IApiRepository, and change it here
        return RetrofitRepository()
    }
}