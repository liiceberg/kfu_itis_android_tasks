package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserCardCrossReference
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserEntity

data class Favourite(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entity = CardEntity::class,
        entityColumn = "id",
        associateBy = Junction(value = UserCardCrossReference::class,
            parentColumn = "user_id",
            entityColumn = "card_id")
    ) val cards: List<CardEntity>
)