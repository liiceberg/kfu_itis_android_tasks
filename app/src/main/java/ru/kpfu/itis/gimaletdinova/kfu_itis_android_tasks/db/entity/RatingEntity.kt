package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ratings",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    ),
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["user_id", "card_id"], unique = true), Index("card_id")]
)
data class RatingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "user_id") val userId: Int?,
    @ColumnInfo(name = "card_id") val cardId: Int?,
    @ColumnInfo val rating: Double
)