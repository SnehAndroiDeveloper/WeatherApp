package com.sneha.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sneha.local_db.models.WeatherEntity

/**
 * Created by Sneha on 15-08-2024.
 */
@Dao
internal interface WeatherEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherEntity(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather where latitude =:latitude and longitude =:longitude")
    suspend fun getWeatherEntity(latitude: String, longitude: String): WeatherEntity?
}