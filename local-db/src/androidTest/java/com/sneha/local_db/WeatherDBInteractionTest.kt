package com.sneha.local_db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sneha.local_db.interactions.WeatherDBInteraction
import com.sneha.local_db.models.WeatherEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sneha on 23-08-2024.
 */
@RunWith(AndroidJUnit4::class)
class WeatherDBInteractionTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var weatherDBInteraction: WeatherDBInteraction

    @Before
    fun setUp() {
        weatherDBInteraction = WeatherDBInteraction(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun insertWeatherEntity_expectedSingleWeatherEntity() = runBlocking {
        val weatherEntity = WeatherEntity(
            id = 0,
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
        weatherDBInteraction.insertWeatherEntity(weatherEntity)

        val result = weatherDBInteraction.getWeatherEntity("22.56", "88.56")
        Assert.assertEquals(weatherEntity, result)
    }

    @Test
    fun insertWeatherEntity_expectedNoWeatherEntity() = runBlocking {
        val weatherEntity = WeatherEntity(
            id = 0,
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
        weatherDBInteraction.insertWeatherEntity(weatherEntity)

        val result = weatherDBInteraction.getWeatherEntity("978.56", "88.56")
        Assert.assertEquals(null, result)
    }
}