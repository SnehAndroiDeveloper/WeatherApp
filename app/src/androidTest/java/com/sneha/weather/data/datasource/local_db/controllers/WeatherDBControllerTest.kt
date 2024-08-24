package com.sneha.weather.data.datasource.local_db.controllers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.MainDispatcherRules
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
class WeatherDBControllerTest {
    @get:Rule
    var mainDispatcherRules = MainDispatcherRules()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var weatherDBController: WeatherDBController

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun insertWeatherEntity_expectedSingleWeatherEntity() = runTest {
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
        weatherDBController.insertWeatherEntity(weatherEntity)

        val result = weatherDBController.getWeatherEntity("22.56", "88.56")
        assertEquals(weatherEntity, result)
    }
}