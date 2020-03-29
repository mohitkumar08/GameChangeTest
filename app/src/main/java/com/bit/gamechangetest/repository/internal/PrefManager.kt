package com.bit.gamechangetest.repository.internal

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.bit.gamechangetest.AppObjectController

const val LAST_SYNC_TIME = "last_sync_time"
object PrefManager {

    @JvmStatic
    private var prefManager: SharedPreferences = this.getPref(AppObjectController.joshApplication)

    private const val PREF_NAME = "GameChangePref.xml"
    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("RestrictedApi")
    private fun getPref(context: Context): SharedPreferences {
        sharedPreferences = PreferenceManager(context).sharedPreferences
        PreferenceManager(context).sharedPreferencesName = PREF_NAME
        return sharedPreferences
    }

    fun getLongValue(key: String): Long {
        return prefManager.getLong(key, 0)

    }

    fun put(key: String, value: Long) {
        prefManager.edit().putLong(key, value).apply()
    }

}