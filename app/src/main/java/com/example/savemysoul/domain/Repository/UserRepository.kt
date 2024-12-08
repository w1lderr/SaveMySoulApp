package com.example.savemysoul.domain.Repository

import com.example.savemysoul.data.Models.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val allUsers: Flow<List<UserEntity>>
    suspend fun insertUser(userEntity: UserEntity)
    suspend fun updateUser(userEntity: UserEntity)
    suspend fun deleteUser(userEntity: UserEntity)
}