package com.example.savemysoul.ui.screens.ShowUsers

import androidx.lifecycle.ViewModel
import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.data.Repository.UserRepositoryImple
import com.example.savemysoul.domain.UseCase.ShowUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ShowUsersViewModel: ViewModel() {
    private val _useCase = ShowUsersUseCase(UserRepositoryImple())
    private val _uiState = MutableStateFlow(ShowUsersUiState())
    private val _selectedUser = MutableStateFlow(UserEntity())
    private val _user = MutableStateFlow(UserEntity())
    val users: Flow<List<UserEntity>> = _useCase.allUsers
    val uiState: Flow<ShowUsersUiState> get() = _uiState
    val selectedUser: Flow<UserEntity> get() = _selectedUser
    val user: Flow<UserEntity> get() = _user

    fun setToast(toast: String) {
        _uiState.value = _uiState.value.copy(toast = toast)
    }

    fun setAlertDialog(alertDialog: Boolean) {
        _uiState.value = _uiState.value.copy(showAlertDialog = alertDialog)
    }

    fun setSelectedUser(userEntity: UserEntity) {
        _selectedUser.value = userEntity
    }

    fun onIdChanged(id: String) {
        _user.value = _user.value.copy(user_id = id)
    }

    fun onMessageChanged(message: String) {
        _user.value = _user.value.copy(user_message = message)
    }

    suspend fun updateUser(userEntity: UserEntity) {
        _useCase.updateUser(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity) {
        _useCase.deleteUser(userEntity)
    }
}