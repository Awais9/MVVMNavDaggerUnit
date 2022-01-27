package com.awais.mvvmnavdaggerunit.network

import com.awais.mvvmnavdaggerunit.animalslist.AnimalListModel
import com.awais.mvvmnavdaggerunit.animalslist.ApiKey
import com.awais.mvvmnavdaggerunit.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class AnimalApiService {

    @Inject
    lateinit var api: AnimalApi

    init {
        DaggerApiComponent.create().apiService(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<AnimalListModel>> {
        return api.getAnimals(key)
    }

}