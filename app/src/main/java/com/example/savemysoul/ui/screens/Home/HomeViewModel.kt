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

    fun setToast(toast: String) {
        _uiState.postValue(_uiState.value?.copy(
            toast = toast
        ))
    }

    fun sendSOS(location: String) {
        val userApi = RetrofitService().getUserApi()
        val users = userList.value ?: emptyList()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (users.isNotEmpty()) {
                    users.forEach {
                        val user = User(
                            id = it.user_id,
                            message = "${it.user_message}. Моя локація: $location"
                        )

                        Log.d("SendMessage", "User ID: ${user.id}, Message: ${user.message}, Location: $location")
                        userApi.sendSOS(user).enqueue(object : Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.isSuccessful) {
                                    setToast(response.message())
                                } else {
                                    setToast(response.errorBody().toString())
                                }
                            }
                            override fun onFailure(call: Call<User>, throwable: Throwable) {
                                setToast(throwable.message.toString())
                            }
                        })
                    }
                } else {
                    setToast("Ваш список користувачів порожній :(")
                }
            } catch (e: Exception) {
                setToast("Помилка: ${e.message} :(")
            }
        }
    }
}