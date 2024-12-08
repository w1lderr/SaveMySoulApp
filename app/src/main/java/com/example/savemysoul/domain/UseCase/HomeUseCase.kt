package com.example.savemysoul.domain.UseCase

import com.example.savemysoul.data.Models.User
import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.domain.Repository.HomeRepository
import com.example.savemysoul.domain.Repository.UserRepository
import kotlinx.coroutines.flow.Flow

class HomeUseCase(
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository
) {
    val allUsers: Flow<List<UserEntity>> get() = userRepository.allUsers

    suspend fun sendSOS(user: User): String {
        val response = homeRepository.sendSOS(user)
        return response
    }
}