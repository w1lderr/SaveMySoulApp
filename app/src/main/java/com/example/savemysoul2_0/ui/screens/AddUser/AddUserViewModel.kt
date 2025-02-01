package com.example.savemysoul2_0.ui.screens.AddUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator
import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.domain.useCase.AddUserScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val _useCase: AddUserScreenUseCase,
    private val _androidUuidGenerator: AndroidUuidGenerator
): ViewModel() {
    private val _uiState = MutableStateFlow(AddUserUiState())
    private val _telegramUser = MutableStateFlow(TelegramUser())
    val uiState: Flow<AddUserUiState> get() = _uiState
    val telegramUser: Flow<TelegramUser> get() = _telegramUser

    fun setToast(toast: String) {
        _uiState.value = _uiState.value.copy(toast = toast)
    }

    fun setIsSuccess(isSuccess: Boolean) {
        _uiState.value = _uiState.value.copy(isSuccess = isSuccess)
    }

    fun onTelegramIdChanged(id: String) {
        _telegramUser.value = _telegramUser.value.copy(telegramId = id)
    }

    fun onTelegramMessageChanged(message: String) {
        _telegramUser.value = _telegramUser.value.copy(telegramMessage = message)
    }

    fun addTelegramUser() {
        val currentTelegramId = _telegramUser.value.telegramId
        val currentTelegramMessage = _telegramUser.value.telegramMessage
        val uuid = _androidUuidGenerator.getOrCreateGuid()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (currentTelegramId.isNotBlank() && currentTelegramMessage.isNotEmpty()) {
                    val response = _useCase.addTelegramUser(TelegramUser(identifier = uuid, telegramId = currentTelegramId, telegramMessage = currentTelegramMessage))
                    setToast(response)
                    setIsSuccess(true)
                } else {
                    withContext(Dispatchers.Main) {
                        setToast("Заповніть всі поля")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setToast("Помилка: $e")
                }
            }
        }
    }
}