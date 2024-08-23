package com.sneha.local_db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sneha.local_db.dao.WeatherEntityDao
import com.sneha.local_db.models.WeatherEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Sneha on 23-08-2024.
 */
class WeatherEntityDaoTest {
    private lateinit var weatherRoomDatabase: WeatherRoomDatabase
    private lateinit var weatherEntityDao: WeatherEntityDao

    @Before
    fun setUp() {
        weatherRoomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        weatherEntityDao = weatherRoomDatabase.weatherEntityDao()
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
        weatherEntityDao.insertWeatherEntity(weatherEntity)

        val result = weatherEntityDao.getWeatherEntity("22.56", "88.56")
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
        weatherEntityDao.insertWeatherEntity(weatherEntity)

        val result = weatherEntityDao.getWeatherEntity("978.56", "88.56")
        Assert.assertEquals(null, result)
    }

    @After
    fun tearDown() {
        weatherRoomDatabase.close()
    }
}