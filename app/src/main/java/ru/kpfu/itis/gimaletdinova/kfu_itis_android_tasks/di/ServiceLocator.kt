package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.AppDatabase

object ServiceLocator {

    private var dbInstance: AppDatabase? = null

    private var preferences: SharedPreferences? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()

        preferences = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    }

    fun getDbInstance(): AppDatabase {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }

    fun getSharedPreferences(): SharedPreferences {
        return preferences ?: throw IllegalStateException("Preferences not initialized")
    }
}
