package com.sneha.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sneha.local_db.dao.WeatherEntityDao
import com.sneha.local_db.models.WeatherEntity

/**
 * Created by Sneha on 15-08-2024.
 */
@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherRoomDatabase : RoomDatabase() {
    internal abstract fun weatherEntityDao(): WeatherEntityDao

    companion object {
        private const val DATABASE_NAME = "weather.db"

        private var dbInstance: WeatherRoomDatabase? = null

        fun getDatabase(context: Context): WeatherRoomDatabase? {
            if (dbInstance == null) {
                synchronized(this) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherRoomDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return dbInstance
        }
    }
}