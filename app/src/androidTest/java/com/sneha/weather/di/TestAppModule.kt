package com.sneha.weather.di

import android.content.Context
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Created by Sneha on 24-08-2024.
 */
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideWeatherPreferences(@ApplicationContext context: Context): WeatherPreferences {
        val preferences = WeatherPreferences
        preferences.init(context)
        return preferences
    }
}