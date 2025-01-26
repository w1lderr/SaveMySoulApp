package com.example.savemysoul2_0.ui.screens.Home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul2_0.data.model.TelegramUserService
import com.example.savemysoul2_0.data.repo.TelegramUserServiceRepoImple
import com.example.savemysoul2_0.domain.useCase.TelegramUserServiceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {
    private val _useCase = MutableStateFlow(TelegramUserServiceUseCase(TelegramUserServiceRepoImple()))
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: Flow<HomeUiState> get() = _uiState

    fun setProgress(showProgress: Boolean) {
        _uiState.value = _uiState.value.copy(showProgress = showProgress)
    }

    fun sendSOS(identifier: String, location: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _useCase.value.sendSOS(TelegramUserService(identifier = identifier, location = location))
                setProgress(true)
                delay(500)
                setProgress(false)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}