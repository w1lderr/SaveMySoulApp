package com.example.savemysoul2_0.ui.screens.ShowUsersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.data.repo.TelegramUserRepoImple
import com.example.savemysoul2_0.domain.useCase.ShowUsersScreenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowUsersViewModel: ViewModel() {
    private val _useCase = ShowUsersScreenUseCase(TelegramUserRepoImple())
    private val _uiState = MutableStateFlow(ShowUsersUiState())
    private val _selectedUser = MutableStateFlow(TelegramUser())
    private val _user = MutableStateFlow(TelegramUser())
    val user: Flow<TelegramUser> get() = _user
    val selectedUser: Flow<TelegramUser> get() = _selectedUser
    val uiState: Flow<ShowUsersUiState> get() = _uiState

    fun setToast(toast: String) {
        _uiState.value = _uiState.value.copy(toast = toast)
    }

    fun setSelectedUser(telegramUser: TelegramUser) {
        _selectedUser.value = telegramUser
    }

    fun onIdChanged(id: String) {
        _user.value = _user.value.copy(telegramId = id)
    }

    fun onMessageChanged(message: String) {
        _user.value = _user.value.copy(telegramMessage = message)
    }

    fun setShowAlertDialog(showAlertDialog: Boolean) {
        _uiState.value = _uiState.value.copy(showAlertDialog = showAlertDialog)
    }

    fun updateTelegramUser(telegramUser: TelegramUser) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = _useCase.updateTelegramUser(telegramUser)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setToast("Помилка: $e")
                }
            }
        }
    }

    fun deleteTelegramUser(telegramUser: TelegramUser) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = _useCase.deleteTelegramUser(telegramUser)
                viewModelScope.launch(Dispatchers.Main) {
                    setToast(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setToast("Помилка: $e")
                }
            }
        }
    }

    fun getTelegramUsers(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = _useCase.getTelegramUsers(uuid)
                _uiState.value = _uiState.value.copy(users = users)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setToast("Помилка: $e")
                }
            }
        }
    }
}