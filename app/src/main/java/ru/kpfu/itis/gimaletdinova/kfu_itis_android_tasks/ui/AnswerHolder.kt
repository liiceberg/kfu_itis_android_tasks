package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.AnswerData
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemAnswerBinding

class AnswerHolder(
    private val binding: ItemAnswerBinding,
    private val onItemChecked: ((Int) -> Unit)? = null,
    private val onRootClicked: ((Int) -> Unit)? = null,
): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.answerRb.setOnClickListener {
            onItemChecked?.invoke(adapterPosition)
        }
        binding.root.setOnClickListener {
            onRootClicked?.invoke(adapterPosition)
        }
    }
    fun bindItem(item: AnswerData) {
        with(binding) {
            answerTv.text = item.answer.toString()
            answerRb.isChecked = item.isChecked
        }
    }
}