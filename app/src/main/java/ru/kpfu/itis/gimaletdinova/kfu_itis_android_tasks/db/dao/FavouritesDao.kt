package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserCardCrossReference
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Favourite

@Dao
interface FavouritesDao {
    @Transaction
    @Query("SELECT * from users where id = :userId;")
    fun get(userId: Int?): Favourite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favourite: UserCardCrossReference)
    @Delete
    fun delete(favourite: UserCardCrossReference)
}