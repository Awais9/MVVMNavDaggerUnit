package com.awais.mvvmnavdaggerunit.di

import com.awais.mvvmnavdaggerunit.animalslist.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DataStoreModule::class, AppModule::class])
interface ViewModelComponent {
    fun listViewModel(viewModel: ListViewModel)
}