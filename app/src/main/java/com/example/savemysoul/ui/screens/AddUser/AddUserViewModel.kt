package com.example.savemysoul.ui.screens.AddUser

import androidx.lifecycle.ViewModel
import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.data.Repository.UserRepositoryImple
import com.example.savemysoul.domain.UseCase.AddUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AddUserViewModel: ViewModel() {
    private val _useCase = AddUserUseCase(UserRepositoryImple())
    private val _uiState = MutableStateFlow(AddUserUiState())
    private val _user = MutableStateFlow(UserEntity())
    val uiState: Flow<AddUserUiState> get() = _uiState
    val user: Flow<UserEntity> get() = _user

    fun setToast(toast: String) {
        _uiState.value = _uiState.value.copy(toast = toast)
    }

    fun setIsSuccess(isSuccess: Boolean) {
        _uiState.value = _uiState.value.copy(isSuccess = isSuccess)
    }

    fun onIdChanged(id: String) {
        _user.value = _user.value.copy(user_id = id)
    }

    fun onMessageChanged(message: String) {
        _user.value = _user.value.copy(user_message = message)
    }

    suspend fun insertUser() {
        val currentId = _user.value.user_id
        val currentMessage = _user.value.user_message

        try {
            if (currentId.isNotEmpty() && currentMessage.isNotEmpty()) {
                _useCase.insertUser(UserEntity(user_id = currentId, user_message = currentMessage))
                setToast("Успішно додано користувача :)")
                setIsSuccess(true)
            } else {
                setToast("Ведіть всі поля : |")
            }
        } catch (e: Exception) {
            setToast("Помилка: $e")
        }
    }
}