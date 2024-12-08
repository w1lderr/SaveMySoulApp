package com.example.savemysoul.data.Repository

import com.example.savemysoul.UserApp
import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.domain.Repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImple: UserRepository {
    private val userDao = UserApp.userDatabase.getUserDao()

    override val allUsers: Flow<List<UserEntity>> get() = userDao.getAllUsers()

    override suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    override suspend fun updateUser(userEntity: UserEntity) {
        userDao.updateUser(userEntity)
    }

    override suspend fun deleteUser(userEntity: UserEntity) {
        userDao.deleteUser(userEntity)
    }
}