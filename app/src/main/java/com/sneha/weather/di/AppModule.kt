package com.sneha.weather.di

import android.content.Context
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Sneha on 21-08-2024.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherPreferences(@ApplicationContext context: Context): WeatherPreferences {
        val preferences = WeatherPreferences
        preferences.init(context)
        return preferences
    }
}