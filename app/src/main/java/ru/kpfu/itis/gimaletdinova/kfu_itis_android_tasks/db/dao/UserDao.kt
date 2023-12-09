package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * from users where email = :email;")
    fun get(email: String): UserEntity?

    @Query("SELECT * from users where id = :userId;")
    fun get(userId: Int?): UserEntity?

    @Query("DELETE from users where id = :userId;")
    fun delete(userId: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserEntity)

    @Query("UPDATE users SET phone = :phone where id = :userId")
    fun updatePhone(userId: Int?, phone: String)

    @Query("UPDATE users SET password = :password where id = :userId")
    fun updatePassword(userId: Int?, password: String)
}