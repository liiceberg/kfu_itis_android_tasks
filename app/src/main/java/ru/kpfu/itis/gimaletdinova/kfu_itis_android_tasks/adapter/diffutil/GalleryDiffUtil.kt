package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Card
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Data

class GalleryDiffUtil(
    private val oldItemsList: List<Data>,
    private val newItemsList: List<Data>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]
        if (oldItem is Card && newItem is Card) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]
        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]
        if (oldItem is Card && newItem is Card) {
            if (oldItem.isLiked != newItem.isLiked) {
                return newItem.isLiked
            }
        }
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}