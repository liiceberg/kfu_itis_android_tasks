package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model

data class CardModel(
    val id: Int,
    var title: String,
    var description: String,
    var instruments: String?,
    var rating: Int?,
    val authorId: Int
)
