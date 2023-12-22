package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity

@Dao
interface CardDao {
    @Query("SELECT * from cards order by production_time")
    fun getAll(): List<CardEntity>?

    @Query("SELECT * from cards where id = :cardId;")
    fun get(cardId: Int?): CardEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun save(card: CardEntity)

    @Query("DELETE from cards where id=:cardId;")
    fun delete(cardId: Int?)
    @Update
    fun update(card: CardEntity)

    @Query("SELECT EXISTS(SELECT 1 from cards where title = :title and production_time = :time)")
    fun isExist(title: String, time: Int) : Boolean
}