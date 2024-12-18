package com.example.savemysoul

import android.app.Application
import androidx.room.Room
import com.example.savemysoul.data.Database.UserDatabase

class UserApp: Application() {
    companion object {
        lateinit var userDatabase: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        userDatabase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            UserDatabase.NAME
        ).build()
    }
}