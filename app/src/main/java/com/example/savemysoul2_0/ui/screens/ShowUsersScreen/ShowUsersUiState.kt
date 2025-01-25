package com.example.savemysoul2_0.ui.screens.ShowUsersScreen

import com.example.savemysoul2_0.data.model.TelegramUser

data class ShowUsersUiState (
    val users: List<TelegramUser> = emptyList(),
    val toast: String = "",
    val showAlertDialog: Boolean = false
)