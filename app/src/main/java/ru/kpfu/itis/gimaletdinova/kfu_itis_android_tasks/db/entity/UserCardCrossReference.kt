package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["user_id", "card_id"])
data class UserCardCrossReference(
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "card_id")  val cardId: Int
)
