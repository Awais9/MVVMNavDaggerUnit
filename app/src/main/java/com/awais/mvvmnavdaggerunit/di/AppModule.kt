package com.awais.mvvmnavdaggerunit.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApp(): Application = application
}