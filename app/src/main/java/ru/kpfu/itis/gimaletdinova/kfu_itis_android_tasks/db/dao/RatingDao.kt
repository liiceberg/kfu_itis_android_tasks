package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.RatingEntity

@Dao
interface RatingDao {
    @Query("SELECT rating from ratings where card_id = :cardId;")
    fun get(cardId: Int?): List<Double>

    @Query("SELECT rating from ratings where user_id = :userId and card_id = :cardId;")
    fun get(userId: Int?, cardId: Int?): Double?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(rating: RatingEntity)

    @Query("DELETE from ratings where user_id = :userId and card_id = :cardId;")
    fun delete(userId: Int?, cardId: Int?)
}