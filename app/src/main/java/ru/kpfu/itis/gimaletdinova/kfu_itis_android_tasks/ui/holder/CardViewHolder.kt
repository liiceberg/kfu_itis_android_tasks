package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Card
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemCardBinding

class CardViewHolder(
    private var binding: ItemCardBinding,
    private val onLikeClicked: (position: Int, card: Card) -> Unit,
    private val onRootClicked: (card: Card, view: View) -> Unit,
    private val onDeleteClicked: (position: Int, card: Card) -> Unit,
    private val deleteOnLongClick: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    private var item: Card? = null

    init {
        with(binding) {
            root.setOnClickListener {
                item?.let { card ->
                    onRootClicked(card, imageIv)
                }
            }
            likeBtnIv.setOnClickListener {
                item?.let {
                    val data = it.copy(isLiked = !it.isLiked)
                    onLikeClicked(adapterPosition, data)
                }
            }
            if (deleteOnLongClick) {
                root.setOnLongClickListener {
                    deleteBtnIv.visibility = if (deleteBtnIv.isVisible) View.GONE else View.VISIBLE
                    true
                }
                deleteBtnIv.setOnClickListener {
                    item?.let {
                        onDeleteClicked(adapterPosition, it)
                    }
                }
            }
        }
    }

    fun bindItem(item: Card) {
        this.item = item
        with(binding) {
            titleTv.text = item.title
            imageIv.setImageResource(item.image)
            changeLikeStatus(item.isLiked)
            imageIv.transitionName = "card-$adapterPosition"
        }
    }

    fun changeLikeStatus(isLiked: Boolean) {
        val likeDrawable = if (isLiked) R.drawable.suit_heart_fill else R.drawable.suit_heart
        binding.likeBtnIv.setImageResource(likeDrawable)
    }
}