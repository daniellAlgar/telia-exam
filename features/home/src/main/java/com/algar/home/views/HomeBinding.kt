package com.algar.home.views

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.algar.model.CurrentForecast
import com.algar.repository.utils.Resource

object HomeBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: Resource<List<CurrentForecast>>?) {
        with(recyclerView.adapter as HomeAdapter) {
            update(items = resource)
        }
    }

    @BindingAdapter("app:showWhenLoading")
    @JvmStatic
    fun <T>showWhenLoading(view: SwipeRefreshLayout, resource: Resource<T>?) {
        resource?.let {
            view.isRefreshing = resource.status == Resource.Status.LOADING
        }
    }
}