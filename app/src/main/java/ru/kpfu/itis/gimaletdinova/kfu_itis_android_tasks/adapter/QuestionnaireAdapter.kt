package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.AnswerData
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemAnswerBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.AnswerHolder

class QuestionnaireAdapter(
    val items: MutableList<AnswerData>,
    private val onItemChecked: ((Int) -> Unit)? = null,
    private val onRootClicked: ((Int) -> Unit)? = null,
) : RecyclerView.Adapter<AnswerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(
            binding = ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemChecked = onItemChecked,
            onRootClicked = onRootClicked
        )
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}