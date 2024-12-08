package com.example.savemysoul.ui.screens.ShowUsers

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.savemysoul.data.Models.UserEntity
import com.example.savemysoul.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowUsersScreen(navController: NavController, viewModel: ShowUsersViewModel = viewModel()) {
    val users by viewModel.users.collectAsState(emptyList())
    val uiState by viewModel.uiState.collectAsState(ShowUsersUiState())
    val selectedUser by viewModel.selectedUser.collectAsState(UserEntity())
    val user by viewModel.user.collectAsState(UserEntity())
    val context = LocalContext.current

    LaunchedEffect(uiState.toast) {
        if (uiState.toast.isNotEmpty()) {
            Toast.makeText(context, uiState.toast, Toast.LENGTH_SHORT).show()
            viewModel.setToast("")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(width = 35.dp, height = 35.dp)
                    .clickable {
                        // Why am I not using popBackStack here? Because this option is more reliable
                        navController.navigate(Screens.HomeScreen.name)
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (users.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Немає користувачів",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
        } else {
            users.let {
                LazyColumn(
                    content = {
                        itemsIndexed(it) { _,item ->
                            UserItem(
                                item = item,
                                onDelete = {
                                    viewModel.viewModelScope.launch(Dispatchers.IO) { viewModel.deleteUser(item) }
                                },
                                onUpdate = {
                                    viewModel.setSelectedUser(item)
                                    viewModel.onIdChanged(item.user_id)
                                    viewModel.onMessageChanged(item.user_message)
                                    viewModel.setAlertDialog(true)
                                }
                            )
                        }
                    }
                )
            }
        }

        if (uiState.showAlertDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.setAlertDialog(false) },
                title = { Text("Редагування") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = user.user_id,
                            onValueChange = { viewModel.onIdChanged(it) },
                            label = { Text("Телеграм айді") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            shape = RoundedCornerShape(8.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = user.user_message,
                            onValueChange = { viewModel.onMessageChanged(it) },
                            label = { Text("Повідомлення") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (user.user_id.isNotEmpty() && user.user_message.isNotEmpty()) {
                                selectedUser.let {
                                    viewModel.viewModelScope.launch {
                                        viewModel.updateUser(
                                            it.copy(user_id = user.user_id, user_message = user.user_message)
                                        )
                                        viewModel.setAlertDialog(false)
                                    }
                                }
                            } else {
                                viewModel.setToast("Ведіть всі поля : |")
                            }
                        }
                    ) {
                        Text(
                            text = "Зберегти",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.setAlertDialog(false) }) {
                        Text(
                            text = "Скасувати",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun UserItem(
    item: UserEntity,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Red)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.user_id,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                text = item.user_message,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        IconButton(onClick = onUpdate) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.White
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}