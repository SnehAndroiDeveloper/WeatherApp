package com.sneha.weather.data.datasource.repositories

import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.MainDispatcherRules
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
import com.sneha.weather.data.datasource.pref.PreferenceKeys
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Created by Sneha on 24-08-2024.
 */
@HiltAndroidTest
class LocationSelectionRepositoryTest {
    @get:Rule
    var mainDispatcherRules = MainDispatcherRules()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var locationSelectionRepository: LocationSelectionRepository

    @Inject
    lateinit var weatherPreferences: WeatherPreferences

    @Inject
    lateinit var dbController: WeatherDBController

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun addLocation_expectedLatitudeAddedInPreference() = runTest {
        val weatherEntity = WeatherEntity(
            temperature = 27,
            feelsLike = 30,
            latitude = "22.56",
            longitude = "88.56",
            location = "Kolkata",
            region = "West Bengal",
            country = "India",
            localtime = "",
            weatherDescription = "Sunny",
            weatherIcon = "",
            sunrise = "5:30:10 AM",
            sunset = "6:10:20 PM",
            humidity = 10,
            uvIndex = 1,
            windSpeed = 11,
            pressure = 1003,
            isDay = true
        )
        locationSelectionRepository.addLocation(weatherEntity)
        val result = weatherPreferences.get(PreferenceKeys.PREF_LATITUDE, "")
        assertEquals("22.56", result)
    }

    @Test
    fun addLocation_expectedLongitudeAddedInPreference() = runTest {
        val weatherEntity = WeatherEntity(
            temperature = 27,
            feelsLike = 30,
            latitude = "22.56",
            longitude = "88.56",
            location = "Kolkata",
            region = "West Bengal",
            country = "India",
            localtime = "",
            weatherDescription = "Sunny",
            weatherIcon = "",
            sunrise = "5:30:10 AM",
            sunset = "6:10:20 PM",
            humidity = 10,
            uvIndex = 1,
            windSpeed = 11,
            pressure = 1003,
            isDay = true
        )
        locationSelectionRepository.addLocation(weatherEntity)
        val result = weatherPreferences.get(PreferenceKeys.PREF_LONGITUDE, "")
        assertEquals("88.56", result)
    }

    @Test
    fun addLocation_expectedEntityAddedInDB() = runTest {
        val weatherEntity = WeatherEntity(
            temperature = 27,
            feelsLike = 30,
            latitude = "22.56",
            longitude = "88.56",
            location = "Kolkata",
            region = "West Bengal",
            country = "India",
            localtime = "",
            weatherDescription = "Sunny",
            weatherIcon = "",
            sunrise = "5:30:10 AM",
            sunset = "6:10:20 PM",
            humidity = 10,
            uvIndex = 1,
            windSpeed = 11,
            pressure = 1003,
            isDay = true
        )
        locationSelectionRepository.addLocation(weatherEntity)
        val result = dbController.getWeatherEntity("22.56", "88.56")
        assertEquals(weatherEntity, result)
    }

    @Test
    fun getWeatherInfo_expectedWeatherInfoReturned() = runTest {
        val result = locationSelectionRepository.getWeatherInfo(22.76, 88.12).toList()
        assertEquals(3, result.size)
    }
}