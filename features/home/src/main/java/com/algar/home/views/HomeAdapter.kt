package com.algar.home.views

import android.view.LayoutInflater.*
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.algar.home.HomeViewModel
import com.algar.home.R
import com.algar.model.CurrentForecast
import com.algar.repository.utils.Resource

class HomeAdapter(private val viewModel: HomeViewModel) : RecyclerView.Adapter<HomeViewHolder>() {

    private val forecast = arrayListOf<CurrentForecast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(view = from(parent.context).inflate(R.layout.current_forecast_item, parent, false))
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindTo(forecast = forecast[position], viewModel = viewModel)
    }

    fun update(items: Resource<List<CurrentForecast>>?) {
        when (items?.status) {
            Resource.Status.SUCCESS,
            Resource.Status.ERROR -> {
                val diffCallback = HomeItemDiffCallback(oldList = forecast, newList = items.data)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                forecast.clear()
                items.data?.let { forecast.addAll(it) }
                diffResult.dispatchUpdatesTo(this)
            }
            else -> {
            }  // No error handling at the moment
        }
    }
}