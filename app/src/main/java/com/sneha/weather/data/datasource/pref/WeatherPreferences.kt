package com.sneha.weather.data.datasource.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import androidx.security.crypto.MasterKey

/**
 * Created by Sneha on 19-08-2024.
 */
object WeatherPreferences {
    private const val PREFS_FILE_NAME = "secret_weather_pref"
    private lateinit var application: Context

    fun init(context: Context) {
        application = context.applicationContext
    }

    fun getSharedPreferences(): SharedPreferences {
        val masterKeyAlias =
            MasterKey.Builder(application).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            application,
            PREFS_FILE_NAME,
            masterKeyAlias,
            PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun save(key: String, value: Any) {
        val sharedPreferences = getSharedPreferences()
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Long -> editor.putLong(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
        }
        editor.apply()
    }

    inline fun <reified T> get(key: String, defaultValue: T): T {
        val sharedPreferences = getSharedPreferences()
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }
}