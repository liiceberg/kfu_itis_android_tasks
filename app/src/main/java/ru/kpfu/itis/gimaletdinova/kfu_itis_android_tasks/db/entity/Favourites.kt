package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Favourites(
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