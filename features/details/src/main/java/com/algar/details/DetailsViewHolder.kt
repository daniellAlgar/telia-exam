package com.algar.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algar.details.databinding.FiveDayForecastItemBinding
import com.algar.model.FiveDayDetails

class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = FiveDayForecastItemBinding.bind(view)

    fun bindTo(forecast: FiveDayDetails, cityName: String) {
        binding.forecast = forecast
        binding.cityName = cityName
    }
}