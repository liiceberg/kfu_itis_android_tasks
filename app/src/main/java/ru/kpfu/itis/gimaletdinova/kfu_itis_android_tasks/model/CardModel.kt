package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model

data class CardModel(
    val id: Int?,
    var title: String,
    val imageUri: String?,
    var description: String,
    var instruments: String?,
    var rating: Double,
    val authorId: Int?,
    val productionTime: Int,
    var isLiked: Boolean = false
)
