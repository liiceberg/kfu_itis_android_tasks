package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Card
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemCardBinding

class CardViewHolder(
    private var binding: ItemCardBinding,
    private val onLikeClicked: (position: Int, card: Card) -> Unit,
    private val onRootClicked: (card: Card) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: Card? = null

    init {
        binding.root.setOnClickListener {
            item?.let(onRootClicked)
        }
        binding.likeBtnIv.setOnClickListener {
            item?.let {
                val data = it.copy(isLiked = !it.isLiked)
                onLikeClicked(adapterPosition, data)
            }
        }
    }

    fun bindItem(item: Card) {
        this.item = item
        with(binding) {
            titleTv.text = item.title
            imageIv.setImageResource(item.image)
            changeLikeStatus(item.isLiked)
        }
    }

    private fun changeLikeStatus(isLiked: Boolean) {
        val likeDrawable = if (isLiked) R.drawable.suit_heart_fill else R.drawable.suit_heart
        binding.likeBtnIv.setImageResource(likeDrawable)
    }
}