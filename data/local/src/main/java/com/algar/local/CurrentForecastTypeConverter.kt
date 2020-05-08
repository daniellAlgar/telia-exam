package com.algar.local

import androidx.room.TypeConverter
import com.algar.model.MainForecast
import com.algar.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentForecastTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun weatherArrayFromString(weathers: String): ArrayList<Weather> {
        val listType = object : TypeToken<ArrayList<Weather>>() {}.type

        return gson.fromJson(weathers, listType)
    }

    @TypeConverter
    fun weatherStringFromArray(weather: ArrayList<Weather>): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun mainForecastFromString(mainForecast: String): MainForecast {
        val listType = object : TypeToken<MainForecast>() {}.type

        return gson.fromJson(mainForecast, listType)
    }

    @TypeConverter
    fun stringFromMainForecast(mainForecast: MainForecast): String {
        return gson.toJson(mainForecast)
    }
}