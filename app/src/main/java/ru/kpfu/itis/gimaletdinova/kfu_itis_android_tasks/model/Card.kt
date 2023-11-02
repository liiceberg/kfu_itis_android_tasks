package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model

import androidx.annotation.DrawableRes

data class Card(
    val id: Int,
    val title: String,
    @DrawableRes val image: Int,
    val description: String,
    var isLiked: Boolean = false
) : Data() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false

        if (title != other.title) return false
        if (image != other.image) return false
        if (description != other.description) return false
        if (isLiked != other.isLiked) return false

        return true
    }
    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + image
        result = 31 * result + description.hashCode()
        result = 31 * result + isLiked.hashCode()
        return result
    }


}


