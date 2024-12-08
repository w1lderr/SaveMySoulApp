package com.example.savemysoul.data.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val key: Int = 0,
    @ColumnInfo("id") val user_id: String = "",
    @ColumnInfo("message") val user_message: String = ""
)