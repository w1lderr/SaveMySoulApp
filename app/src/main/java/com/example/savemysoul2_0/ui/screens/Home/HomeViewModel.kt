package com.example.savemysoul2_0.ui.screens.Home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator
import com.example.savemysoul2_0.data.model.TelegramUserService
import com.example.savemysoul2_0.domain.useCase.HomeScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val _useCase: HomeScreenUseCase,
    private val _androidUuidGenerator: AndroidUuidGenerator,
    val locationUtils: LocationUtils
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: Flow<HomeUiState> get() = _uiState

    fun setProgress(showProgress: Boolean) {
        _uiState.value = _uiState.value.copy(showProgress = showProgress)
    }

    fun sendSOS(location: String, context: Context) {
        val uuid = _androidUuidGenerator.getOrCreateGuid()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _useCase.sendSOS(TelegramUserService(identifier = uuid, location = location))
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