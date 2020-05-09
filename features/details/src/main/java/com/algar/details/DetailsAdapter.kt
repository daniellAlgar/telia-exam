package com.algar.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algar.model.FiveDayDetails
import android.view.LayoutInflater.from
import androidx.recyclerview.widget.DiffUtil
import com.algar.model.FiveDayForecast
import com.algar.repository.utils.Resource
import com.algar.repository.utils.Resource.Status.*

class DetailsAdapter(private val cityName: String) : RecyclerView.Adapter<DetailsViewHolder>() {

    private val forecast = arrayListOf<FiveDayDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder(view = from(parent.context).inflate(R.layout.five_day_forecast_item, parent, false))
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bindTo(forecast = forecast[position], cityName = cityName)
    }

    fun update(items: Resource<FiveDayForecast>?) {
        when (items?.status) {
            SUCCESS,
            ERROR -> {
                val diffCallback = DetailsItemDiffCallback(
                    oldList = forecast,
                    newList = items.data?.list
                )
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                forecast.clear()
                items.data?.let { forecast.addAll(it.list) }
                diffResult.dispatchUpdatesTo(this)
            }
            else -> {} // No error (and loading) handling at the moment
        }
    }
}