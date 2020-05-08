package com.algar.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.algar.model.CurrentForecast

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentForecast: ArrayList<CurrentForecast>)

    @Query("SELECT * FROM CurrentForecast")
    fun getCurrentForecast(): LiveData<List<CurrentForecast>>
}