package com.algar.home.views

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.algar.model.CurrentForecast
import com.algar.model.Secrets
import com.algar.repository.utils.Resource
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

object HomeBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: Resource<List<CurrentForecast>>?) {
        with(recyclerView.adapter as HomeAdapter) {
            update(items = resource)
        }
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

    @BindingAdapter("app:loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, code: String?) {
        val imageUrl = Secrets.iconUrl + code + "@2x.png"
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}