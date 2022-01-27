package com.awais.mvvmnavdaggerunit.animalslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.awais.mvvmnavdaggerunit.di.AppModule
import com.awais.mvvmnavdaggerunit.di.CONTEXT_APP
import com.awais.mvvmnavdaggerunit.di.DaggerViewModelComponent
import com.awais.mvvmnavdaggerunit.di.TypeOfContext
import com.awais.mvvmnavdaggerunit.network.AnimalApiService
import com.awais.mvvmnavdaggerunit.utils.DataStoreHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = true
    }

    val animals by lazy { MutableLiveData<List<AnimalListModel>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    private var invalidKey = false
    private var injected = false

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var dataStoreHelper: DataStoreHelper

    fun inject() {
        if (!injected) {
            DaggerViewModelComponent.builder().appModule(AppModule(getApplication())).build()
                .listViewModel(this)
        }
    }

    suspend fun refresh(hardRefresh: Boolean) {
        inject()
        if (animals.value.isNullOrEmpty() || hardRefresh) {
            loading.value = true
            invalidKey = false
            dataStoreHelper.getApiKey().collect { key ->
                if (key.isNullOrEmpty()) {
                    getKey()
                } else {
                    getAnimals(key)
                }
            }
        }
    }

    private fun getKey() {
        viewModelScope.launch {
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(model: ApiKey) {
                        if (model.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            viewModelScope.launch {
                                dataStoreHelper.saveApiKey(model.key)
                                getAnimals(model.key)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        loadError.value = true
                    }
                })
        }
    }

    private fun getAnimals(key: String) {
        viewModelScope.launch {
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<AnimalListModel>>() {
                    override fun onSuccess(list: List<AnimalListModel>) {
                        loading.value = false
                        loadError.value = false
                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidKey) {
                            invalidKey = true
                            getKey()
                        } else {
                            loading.value = false
                            animals.value = null
                            loadError.value = true
                        }
                    }
                })
        }
    }
}