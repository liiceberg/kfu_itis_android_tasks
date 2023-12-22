package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity

@Dao
interface CardDao {
    @Query("SELECT * from cards")
    fun getAll(): List<CardEntity>?

    @Query("SELECT * from cards where id = :cardId;")
    fun get(cardId: Int?): CardEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun save(card: CardEntity)

    @Query("UPDATE cards set rating = :rating where id = :cardId;")
    fun update(cardId: Int?, rating: Double)
    @Query("DELETE from cards where id = :cardId;")
    fun delete(cardId: Int?)

    @Query("SELECT EXISTS(SELECT 1 from cards where title = :title and production_time = :time)")
    fun isExist(title: String, time: Int) : Boolean
}