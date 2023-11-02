package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.diffutil.GalleryDiffUtil
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemCardBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemDateBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Card
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Data
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Date
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder.CardViewHolder
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder.DateViewHolder
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.DataType

class GalleryAdapter(
    private val layoutManager: RecyclerView.LayoutManager,
    private val onLikeClicked: (position: Int, card: Card) -> Unit,
    private val onRootClicked: (card: Card, view: View) -> Unit,
    private val onDeleteClicked: (position: Int, card: Card) -> Unit,
    private val deleteOnLongClick: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Data>()

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Card -> DataType.CARD.value
            is Date -> DataType.DATE.value
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            DataType.CARD.value -> CardViewHolder(
                ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onLikeClicked,
                onRootClicked,
                onDeleteClicked,
                deleteOnLongClick
            )

            DataType.DATE.value -> DateViewHolder(
                ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> {
                throw RuntimeException("Such view type not found")
            }
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        with(holder) {
            when (this) {
                is CardViewHolder -> bindItem(items[position] as Card)
                is DateViewHolder -> {
                    if (layoutManager is StaggeredGridLayoutManager) {
                        (itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                            true
                    }
                    bindItem(items[position] as Date)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItem(position: Int, item: Card) {
        items[position] = item
        notifyItemChanged(position, item.isLiked)
    }

    fun setItems(list: List<Data>) {
        val diff = GalleryDiffUtil(oldItemsList = items, newItemsList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insertItem(position: Int, card: Card) {
        items.add(position, card)
        notifyItemInserted(position)
    }
}