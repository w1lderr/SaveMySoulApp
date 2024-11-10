package com.example.savemysoul.ui.screens.ShowUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul.MainApplication
import com.example.savemysoul.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowUsersViewModel: ViewModel() {
    private val userDao = MainApplication.userDatabase.getUserDao()
    val userList: LiveData<List<UserEntity>> = userDao.getAllUsers()

    fun deleteUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteUser(id)
        }
    }
}