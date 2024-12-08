package com.example.savemysoul.domain.UseCase

import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.domain.Repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ShowUsersUseCase(private val userRepository: UserRepository) {
    val allUsers: Flow<List<UserEntity>> get() = userRepository.allUsers

    suspend fun updateUser(userEntity: UserEntity) {
        userRepository.updateUser(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity) {
        userRepository.deleteUser(userEntity)
    }
}