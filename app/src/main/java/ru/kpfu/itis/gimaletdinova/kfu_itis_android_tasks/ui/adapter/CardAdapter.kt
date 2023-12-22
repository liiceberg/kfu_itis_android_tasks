package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemCardBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.CardModel
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder.CardHolder

class CardAdapter(
    diffCallback: DiffUtil.ItemCallback<CardModel>,
    private val glide: RequestManager,
    private val onLikeClicked: (position: Int, card: CardModel) -> Unit,
    private val onCardClicked: (card: CardModel) -> Unit,
    private val onDeleteClicked: (position: Int, card: CardModel) -> Unit,
) : ListAdapter<CardModel, CardHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(
            binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onDeleteClicked = onDeleteClicked,
            onLikeClicked = onLikeClicked,
            onCardClicked = onCardClicked,
            glide = glide
        )
    }
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        (holder as? CardHolder)?.bindItem(getItem(position))
    }
    fun setItems(items: List<CardModel>) {
        submitList(items)
    }

    fun updateItem(position: Int, item: CardModel) {
        val list = currentList.toMutableList()
        list[position] = item
        submitList(list)
    }

    fun removeItem(position: Int) {
        val list = currentList.toMutableList()
        list.removeAt(position)
        submitList(list)
    }

    fun addItem(position: Int, item: CardModel) {
        val list = currentList.toMutableList()
        list.add(position, item)
        submitList(list)
    }

}