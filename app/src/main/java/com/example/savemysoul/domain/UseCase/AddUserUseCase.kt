package com.example.savemysoul.domain.UseCase

import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.domain.Repository.UserRepository

class AddUserUseCase(private val userRepository: UserRepository) {
    suspend fun insertUser(userEntity: UserEntity) {
        userRepository.insertUser(userEntity)
    }
}