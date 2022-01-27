package com.awais.mvvmnavdaggerunit.di

import com.awais.mvvmnavdaggerunit.network.AnimalApiService

class ApiModuleTest(private val mockService: AnimalApiService) : ApiModule() {
    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}