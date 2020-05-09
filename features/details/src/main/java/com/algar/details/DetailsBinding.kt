package com.algar.details

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.algar.model.FiveDayDetails
import com.algar.model.FiveDayForecast
import com.algar.repository.utils.Resource

object DetailsBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: Resource<FiveDayForecast>?) {
        with(recyclerView.adapter as DetailsAdapter) {
            update(items = resource)
        }
    }
}