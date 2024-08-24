package com.sneha.weather.data.datasource.repositories

import com.sneha.weather.MainDispatcherRules
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
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
class DashboardRepositoryTest {
    @get:Rule
    var mainDispatcherRules = MainDispatcherRules()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dashboardRepository: DashboardRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun getCurrentLocationWeatherInfoTest() = runTest {
        val result = dashboardRepository.getCurrentLocationWeatherInfo("22.76", "88.12").toList()
        assertEquals(6, result.size)
    }
}