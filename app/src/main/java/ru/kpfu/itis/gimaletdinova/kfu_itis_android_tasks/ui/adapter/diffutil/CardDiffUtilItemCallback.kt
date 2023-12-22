package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.CardModel

class CardDiffUtilItemCallback : DiffUtil.ItemCallback<CardModel>()  {
    override fun areItemsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem.isLiked == newItem.isLiked
    }

    override fun getChangePayload(oldItem: CardModel, newItem: CardModel): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}