package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cards",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class, parentColumns = ["id"],
        childColumns = ["author_id"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["title", "rating"], unique = true)]
)
data class CardEntity(
    @PrimaryKey val id: Int,
    var title: String,
    var description: String,
    var instruments: String?,
    var rating: Int?,
    @ColumnInfo(name = "production_time") val productionTime: Int,
    @ColumnInfo(name = "author_id") val authorId: Int
)