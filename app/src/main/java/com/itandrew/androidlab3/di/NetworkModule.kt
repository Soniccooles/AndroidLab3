package com.itandrew.androidlab3.di

import com.itandrew.androidlab3.data.BulbApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideLightService() : BulbApiService =
        Retrofit.Builder()
            .baseUrl("http://195.133.53.179:1337/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BulbApiService::class.java)
}