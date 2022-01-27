package com.awais.mvvmnavdaggerunit.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.awais.mvvmnavdaggerunit.utils.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class SharedPreferenceModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    open fun provideDataStore(app: Application): SharedPreferenceHelper {
        return SharedPreferenceHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    open fun providesActivityDataStore(activity: AppCompatActivity): SharedPreferenceHelper {
        return SharedPreferenceHelper(activity)
    }
}

const val CONTEXT_APP = "Application context"
const val CONTEXT_ACTIVITY = "Activity context"

@Qualifier
annotation class TypeOfContext(val type:String)