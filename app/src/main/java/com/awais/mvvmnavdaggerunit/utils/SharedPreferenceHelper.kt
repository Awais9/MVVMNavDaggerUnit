package com.awais.mvvmnavdaggerunit.utils

import android.content.Context
import androidx.preference.PreferenceManager

class SharedPreferenceHelper(context: Context) {
    private val PREF_API_KEY = "api_key"
    private val pref = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key: String) {
        pref.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey() = pref.getString(PREF_API_KEY, "")

}