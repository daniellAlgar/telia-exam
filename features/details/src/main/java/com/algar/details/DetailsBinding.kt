package com.algar.details

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.algar.model.FiveDayDetails
import com.algar.model.FiveDayForecast
import com.algar.model.Secrets
import com.algar.model.prettyDate
import com.algar.repository.utils.Resource
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

object DetailsBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: Resource<FiveDayForecast>?) {
        with(recyclerView.adapter as DetailsAdapter) {
            update(items = resource)
        }
    }

    @BindingAdapter("app:date")
    @JvmStatic
    fun setDate(view: TextView, forecast: FiveDayDetails) {
        view.text = forecast.prettyDate()
    }

    @BindingAdapter("app:loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, code: String?) {
        val imageUrl = Secrets.iconUrl + code + "@2x.png"
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setFloat(view: TextView, value: Float?) {
        view.text = if (value == null || value.isNaN()) {
            "-"
        } else {
            val celsiusSymbol = "\u2103"
            val tempWithoutDecimal = value.roundToInt()
            "$tempWithoutDecimal$celsiusSymbol"
        }
    }
}