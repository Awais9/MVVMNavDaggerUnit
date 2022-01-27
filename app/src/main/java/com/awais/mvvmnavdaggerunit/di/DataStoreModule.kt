package com.awais.mvvmnavdaggerunit.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.awais.mvvmnavdaggerunit.utils.DataStoreHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class DataStoreModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    open fun provideDataStore(app: Application): DataStoreHelper {
        return DataStoreHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    open fun provideActivityDataStore(activity: AppCompatActivity): DataStoreHelper {
        return DataStoreHelper(activity)
    }

}

const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY = "Activity Context"

@Qualifier
annotation class TypeOfContext(val type: String)