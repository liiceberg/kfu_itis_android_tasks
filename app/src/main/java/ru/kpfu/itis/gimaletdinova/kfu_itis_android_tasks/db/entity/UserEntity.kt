package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    indices = [Index(value = ["email"], unique = true), Index(value = ["phone"], unique = true)])
data class UserEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var username: String,
    var email: String,
    var phone: String,
    var password: String
)