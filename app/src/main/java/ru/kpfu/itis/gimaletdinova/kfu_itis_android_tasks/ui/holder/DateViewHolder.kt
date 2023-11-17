package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder

import android.annotation.SuppressLint

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ItemDateBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Date
import java.text.SimpleDateFormat


class DateViewHolder(private var binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bindItem(date: Date) {
        with(binding) {
            dateTv.text = date.value
        }
    }
}