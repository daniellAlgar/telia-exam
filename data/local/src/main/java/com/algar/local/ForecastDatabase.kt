package com.algar.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.algar.local.dao.ForecastDao
import com.algar.model.CurrentForecast

@TypeConverters(CurrentForecastTypeConverter::class)
@Database(
    version = 1,
    entities = [CurrentForecast::class]
)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java,
                "TeliaExam"
            ).build()
    }
}