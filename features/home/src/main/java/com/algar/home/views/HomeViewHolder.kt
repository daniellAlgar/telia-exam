package com.algar.home.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algar.home.HomeViewModel
import com.algar.home.databinding.CurrentForecastItemBinding
import com.algar.model.CurrentForecast

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = CurrentForecastItemBinding.bind(view)

    fun bindTo(forecast: CurrentForecast, viewModel: HomeViewModel) {
        binding.currentForecast = forecast
        binding.viewModel = viewModel
    }
}