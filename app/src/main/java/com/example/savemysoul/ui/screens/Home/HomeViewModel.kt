package com.example.savemysoul.ui.screens.Home

import androidx.lifecycle.ViewModel
import com.example.savemysoul.data.Models.User
import com.example.savemysoul.data.Repository.HomeRepositoryImple
import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.data.Repository.UserRepositoryImple
import com.example.savemysoul.domain.UseCase.HomeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class HomeViewModel: ViewModel() {
    private val _useCase = HomeUseCase(UserRepositoryImple(), HomeRepositoryImple())
    private val _uiState = MutableStateFlow(HomeUiState())
    private val userList: Flow<List<UserEntity>> get() = _useCase.allUsers
    val uiState: Flow<HomeUiState> get() = _uiState

    private fun setProgressBar(progress: Float) {
        _uiState.value = _uiState.value.copy(progressBar = progress)
    }

    private fun showProgressBar(showProgressBar: Boolean) {
        _uiState.value = _uiState.value.copy(showProgressBar = showProgressBar)
    }

    fun setToast(toast: String) {
        _uiState.value = _uiState.value.copy(toast = toast)
    }

    suspend fun sendSOS(latitude: Double, longitude: Double) {
        val users = userList.first()

        try {
            if (users.isNotEmpty()) {
                var sentCount = 0
                showProgressBar(true)
                users.forEach {
                    val user = User(
                        id = it.user_id,
                        message = "${it.user_message}. <a href=\"https://www.google.com/maps?q=$latitude,$longitude\"><b>Моя локація</b></a>"
                    )
                    val response = _useCase.sendSOS(user)
                    setToast(response)
                    sentCount++
                    setProgressBar(sentCount.toFloat() / users.size)
                }
                delay(300)
                setProgressBar(0f)
                showProgressBar(false)
            } else {
                setToast("Ваш список користувачів порожній :(")
            }
        } catch (e: Exception) {
            setToast("Помилка: ${e.message}")
        }
    }
}