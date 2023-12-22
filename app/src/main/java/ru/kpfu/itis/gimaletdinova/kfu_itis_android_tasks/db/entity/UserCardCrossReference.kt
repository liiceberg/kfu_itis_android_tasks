package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "favourites",
    primaryKeys = ["user_id", "card_id"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("card_id")]
)
data class UserCardCrossReference(
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "card_id") val cardId: Int
)
