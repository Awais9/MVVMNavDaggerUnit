package com.awais.mvvmnavdaggerunit.di

import android.app.Application
import com.awais.mvvmnavdaggerunit.utils.DataStoreHelper

class DataStoreModuleTest(private val mockDataStoreHelper: DataStoreHelper) : DataStoreModule() {
    override fun provideDataStore(app: Application): DataStoreHelper {
        return mockDataStoreHelper
    }
}