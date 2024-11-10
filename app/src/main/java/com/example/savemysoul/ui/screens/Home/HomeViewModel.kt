package com.example.savemysoul.ui.screens.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul.MainApplication
import com.example.savemysoul.api.RetrofitService
import com.example.savemysoul.data.User
import com.example.savemysoul.data.UserEntity
import com.example.savemysoul.ui.screens.AddUser.AddUserUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val userDao = MainApplication.userDatabase.getUserDao()
    val userList: LiveData<List<UserEntity>> = userDao.getAllUsers()

    private val _uiState = MutableLiveData(HomeUiState())
    val uiState: LiveData<HomeUiState> get() = _uiState

    private val _location = MutableLiveData<String>()

    fun setLocation(location: String) {
        _location.value = location
    }

    fun clearToast() {
        _uiState.postValue(_uiState.value?.copy(
            toast = ""
        ))
    }

    fun sendSOS() {
        val userApi = RetrofitService().getUserApi()
        val users = userList.value ?: emptyList()
        val location = _location.value ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (users.isNotEmpty()) {
                    users.forEach {
                        val user = User(
                            id = it.user_id,
                            message = "${it.user_message}. Моя локація: ${location}"
                        )

                        Log.d("SendMessage", "User ID: ${user.id}, Message: ${user.message}")
                        userApi.sendSOS(user).enqueue(object : Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.isSuccessful) {
                                    _uiState.postValue(_uiState.value?.copy(
                                        toast = response.message()
                                    ))
                                } else {
                                    _uiState.postValue(_uiState.value?.copy(
                                        toast = response.errorBody().toString()
                                    ))
                                }
                            }
                            override fun onFailure(call: Call<User>, throwable: Throwable) {
                                _uiState.postValue(_uiState.value?.copy(
                                    toast = throwable.message.toString()
                                ))
                            }
                        })
                    }
                } else {
                    _uiState.postValue(_uiState.value?.copy(
                        toast = "Ваш список користувачів порожній."
                    ))
                }
            } catch (e: Exception) {
                _uiState.postValue(_uiState.value?.copy(
                    toast = "Помилка: ${e.message}."
                ))
            }
        }
    }
}