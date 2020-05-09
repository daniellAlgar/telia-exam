package com.algar.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.algar.model.CurrentForecast
import com.algar.model.FiveDayForecast

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentForecast: ArrayList<CurrentForecast>)

    @Query("SELECT * FROM CurrentForecast")
    fun getCurrentForecast(): LiveData<List<CurrentForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fiveDayForecast: FiveDayForecast)

    @Query("SELECT * FROM FiveDayForecast")
    fun getFiveDayForecast(): LiveData<FiveDayForecast>
}