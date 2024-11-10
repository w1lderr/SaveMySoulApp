package com.example.savemysoul.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Insert
    fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM user_table where id = :id")
    fun deleteUser(id: Int)
}