package com.sneha.weather.data.datasource.pref

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Created by Sneha on 24-08-2024.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WeatherPreferencesTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var weatherPreferences: WeatherPreferences

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun insertLatitude_expectedLatitudeValue() {
        weatherPreferences.save(PreferenceKeys.PREF_LATITUDE, "12.34")
        val result = weatherPreferences.get(PreferenceKeys.PREF_LATITUDE, "")
        assertEquals("12.34", result)
    }

    @Test
    fun insertLongitude_expectedLongitudeValue() {
        weatherPreferences.save(PreferenceKeys.PREF_LONGITUDE, "22.84")
        val result = weatherPreferences.get(PreferenceKeys.PREF_LONGITUDE, "")
        assertEquals("22.84", result)
    }
}