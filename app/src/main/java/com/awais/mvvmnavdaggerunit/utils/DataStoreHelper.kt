package com.awais.mvvmnavdaggerunit.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


class DataStoreHelper(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            "MVVMNavDaggerUnit"
        )
        private val apiKey = stringPreferencesKey("API_KEY")
    }

    suspend fun saveApiKey(key: String) {
        context.dataStore.edit {
            it[apiKey] = key
        }
    }

    suspend fun getApiKey() = context.dataStore.data.map { it[apiKey] ?: "" }
}