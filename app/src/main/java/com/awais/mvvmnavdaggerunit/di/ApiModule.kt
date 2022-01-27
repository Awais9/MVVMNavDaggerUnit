package com.awais.mvvmnavdaggerunit.di

import com.awais.mvvmnavdaggerunit.network.AnimalApi
import com.awais.mvvmnavdaggerunit.network.AnimalApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {
    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    @Provides
    fun provideAnimalApi(): AnimalApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(AnimalApi::class.java)
    }

    @Provides
    open fun provideAnimalApiService(): AnimalApiService {
        return AnimalApiService()
    }


}