package com.algar.home.views

import androidx.recyclerview.widget.DiffUtil
import com.algar.model.CurrentForecast

class HomeItemDiffCallback(
    private val oldList: List<CurrentForecast>,
    private val newList: List<CurrentForecast>?
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        newList ?: return false
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        newList ?: return false
        return oldList[oldItemPosition].dt == newList[newItemPosition].dt
    }
}