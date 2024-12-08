package com.example.savemysoul.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.savemysoul.data.Dao.UserDao
import com.example.savemysoul.data.Models.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    companion object {
        const val NAME = "user_database"
    }

    abstract fun getUserDao(): UserDao
}