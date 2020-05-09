package com.algar.details

import androidx.recyclerview.widget.DiffUtil
import com.algar.model.FiveDayDetails

class DetailsItemDiffCallback(
    private val oldList: ArrayList<FiveDayDetails>,
    private val newList: ArrayList<FiveDayDetails>?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        newList ?: return false
        return oldList[oldItemPosition].dt == newList[newItemPosition].dt
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        newList ?: return false
        return oldList[oldItemPosition].dt == newList[newItemPosition].dt
    }

}
