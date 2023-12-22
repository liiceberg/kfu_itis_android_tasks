package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao.CardDao
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao.FavouritesDao
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao.RatingDao
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.dao.UserDao
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.RatingEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserCardCrossReference
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, CardEntity::class, UserCardCrossReference::class, RatingEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val cardDao: CardDao
    abstract val favouritesDao: FavouritesDao
    abstract val ratingDao: RatingDao

    companion object {
        const val DATABASE_NAME = "application.db"
    }

}