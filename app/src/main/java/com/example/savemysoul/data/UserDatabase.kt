package com.example.savemysoul.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    companion object {
        const val NAME = "user_database"
    }

    abstract fun getUserDao(): UserDao
}