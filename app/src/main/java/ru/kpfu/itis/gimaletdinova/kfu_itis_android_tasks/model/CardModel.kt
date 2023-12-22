package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model

import java.io.Serializable

data class CardModel(
    val id: Int?,
    val title: String,
    val imageUri: String?,
    val description: String,
    val instruments: String,
    val difficulty: String,
    var rating: Double,
    val authorId: Int?,
    val productionTime: Int,
    var isLiked: Boolean = false
) : Serializable
