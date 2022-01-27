package com.awais.mvvmnavdaggerunit.di

import android.app.Application
import com.awais.mvvmnavdaggerunit.utils.SharedPreferenceHelper

class PrefModuleTest(private val mockPref: SharedPreferenceHelper) : SharedPreferenceModule() {
    override fun provideDataStore(app: Application): SharedPreferenceHelper {
        return mockPref
    }
}