package com.awais.mvvmnavdaggerunit.di

import com.awais.mvvmnavdaggerunit.network.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun apiService(service: AnimalApiService)

}