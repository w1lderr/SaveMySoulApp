package com.example.savemysoul.ui.screens.AddUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul.MainApplication
import com.example.savemysoul.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUserViewModel: ViewModel() {
    private val userDao = MainApplication.userDatabase.getUserDao()

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _userMessage = MutableLiveData<String>()
    val userMessage: LiveData<String> get() = _userMessage

    private val _uiState = MutableLiveData(AddUserUiState())
    val uiState: LiveData<AddUserUiState> get() = _uiState

    fun onIdChanged(id: String) {
        _userId.value = id
    }

    fun onMessageChanged(message: String) {
        _userMessage.value = message
    }

    fun insertUser() {
        val currentId = _userId.value ?: ""
        val currentMessage = _userMessage.value ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (currentId.isNotEmpty() && currentMessage.isNotEmpty()) {
                    userDao.insertUser(UserEntity(user_id = currentId, user_message = currentMessage))
                    _uiState.postValue(_uiState.value?.copy(
                        toast = "Успішно додано користувача.",
                        isSuccess = true
                    ))
                } else {
                    _uiState.postValue(_uiState.value?.copy(
                        toast = "Будь ласка ведіть всі поля."
                    ))
                }
            } catch (e: Exception) {
                _uiState.postValue(_uiState.value?.copy(
                    toast = "Помилка: ${e.message}."
                ))
            }
        }
    }

    fun clearToast() {
        _uiState.postValue(_uiState.value?.copy(
            toast = ""
        ))
    }

    fun clearIsSuccess() {
        _uiState.postValue(_uiState.value?.copy(
            isSuccess = null
        ))
    }
}