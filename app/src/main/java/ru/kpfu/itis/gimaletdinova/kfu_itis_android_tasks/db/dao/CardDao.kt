package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity

@Dao
interface CardDao {
    @Query("SELECT * from cards")
    fun getAll(): List<CardEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(card: CardEntity)

    @Delete
    fun delete(card: CardEntity)
}