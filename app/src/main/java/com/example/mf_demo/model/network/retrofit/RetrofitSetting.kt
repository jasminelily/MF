package com.example.mf_demo.model.network.retrofit

import com.example.mf_demo.model.api.retrofit.RetrofitApiService
import com.example.mf_demo.util.constant.CPath
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSetting {
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(CPath.APIHeaderType.Auth.type, CPath.API_AUTH)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    private fun provideRetrofit(): Retrofit {
        //format rule
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .baseUrl(CPath.BASE_API_URL)
            .client(provideOkHttpClient())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiRetrofit: RetrofitApiService by lazy {
        provideRetrofit().create(RetrofitApiService::class.java)
    }

} 