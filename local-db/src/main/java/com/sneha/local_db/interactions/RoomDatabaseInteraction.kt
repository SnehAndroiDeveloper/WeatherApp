package com.sneha.local_db.interactions

import android.content.Context
import androidx.annotation.CallSuper
import com.sneha.local_db.WeatherRoomDatabase

/**
 * Created by Sneha on 15-08-2024.
 */
open class RoomDatabaseInteraction {
    @CallSuper
    fun getDBInstance(context: Context): WeatherRoomDatabase? =
        WeatherRoomDatabase.getDatabase(context)
}