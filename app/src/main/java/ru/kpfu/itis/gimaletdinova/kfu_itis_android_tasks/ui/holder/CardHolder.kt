package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemCardBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.CardModel
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser

class CardHolder(
    private val binding: ItemCardBinding,
    private val onLikeClicked: (position: Int, card: CardModel) -> Unit,
    private val onCardClicked: (card: CardModel) -> Unit,
    private val onDeleteClicked: (position: Int, card: CardModel) -> Unit,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    private var item: CardModel? = null

    init {
        with(binding) {
            root.setOnClickListener {
                item?.let(onCardClicked)
            }
            likeBtnIv.setOnClickListener {
                item?.let { card ->
                    val itemCopy = card.copy(isLiked = !card.isLiked)
                    changeLikeBtnStatus(itemCopy.isLiked)
                    onLikeClicked(adapterPosition, itemCopy)
                }
            }
            deleteBtnIv.setOnClickListener {
                item?.let {
                    onDeleteClicked(adapterPosition, it)
                }
            }
        }

    }

    fun bindItem(item: CardModel) {
        this.item = item
        with(binding) {
            titleTv.text = item.title
            changeLikeBtnStatus(item.isLiked)
            deleteBtnIv.visibility =
                if (item.authorId == CurrentUser.userId) View.VISIBLE else View.GONE
            glide.load(item.imageUri)
                .error(R.drawable.card_img)
                .into(imageIv)
        }
    }

    private fun changeLikeBtnStatus(isLiked: Boolean) {
        val color = if (isLiked) R.color.black else R.color.grey
        binding.likeBtnIv.setColorFilter(color)
    }
}