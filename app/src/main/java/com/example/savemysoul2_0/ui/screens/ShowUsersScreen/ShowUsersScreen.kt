package com.example.savemysoul2_0.ui.screens.ShowUsersScreen

import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowUsersScreen(navController: NavController, viewModel: ShowUsersViewModel = hiltViewModel()) {
    val user = viewModel.user.collectAsState(TelegramUser())
    val selectedUser = viewModel.selectedUser.collectAsState(TelegramUser())
    val uiState = viewModel.uiState.collectAsState(ShowUsersUiState())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getTelegramUsers()
    }

    LaunchedEffect(uiState.value.toast) {
        if (uiState.value.toast.isNotEmpty()) {
            Toast.makeText(context, uiState.value.toast, Toast.LENGTH_SHORT).show()
            viewModel.setToast("")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
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
                tint = Color.White,
                modifier = Modifier
                    .size(width = 35.dp, height = 35.dp)
                    .clickable {
                        // Why i'm not using popBackStack here? Because this option is more reliable
                        navController.navigate(Screens.HomeScreen.name)
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.value.users.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Немає користувачів",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        } else {
            uiState.value.users.let {
                LazyColumn(
                    content = {
                        itemsIndexed(it) { _,item ->
                            Log.d("ITEM", item.toString())
                            UserItem(
                                item = item,
                                onDelete = {
                                    viewModel.deleteTelegramUser(item)
                                },
                                onUpdate = {
                                    viewModel.setSelectedUser(item)
                                    viewModel.onIdChanged(item.telegramId)
                                    viewModel.onMessageChanged(item.telegramMessage)
                                    viewModel.setShowAlertDialog(true)
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    if (uiState.value.showAlertDialog) {
        ShowAlertDialog(viewModel, user.value, selectedUser.value)
    }
}

@Composable
fun ShowAlertDialog(viewModel: ShowUsersViewModel, user: TelegramUser, selectedUser: TelegramUser) {
    AlertDialog(
        onDismissRequest = { viewModel.setShowAlertDialog(false) },
        title = { Text("Редагування") },
        text = {
            Column {
                OutlinedTextField(
                    value = user.telegramId,
                    onValueChange = { viewModel.onIdChanged(it) },
                    label = { Text("Телеграм айді") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(15.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = user.telegramMessage,
                    onValueChange = { viewModel.onMessageChanged(it) },
                    label = { Text("Повідомлення") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(15.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (user.telegramId.isNotEmpty() && user.telegramMessage.isNotEmpty()) {
                        selectedUser.let {
                            viewModel.updateTelegramUser(it.copy(telegramId = user.telegramId, telegramMessage = user.telegramMessage))
                            viewModel.setShowAlertDialog(false)
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
            TextButton(onClick = { viewModel.setShowAlertDialog(false) }) {
                Text(
                    text = "Скасувати",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    )
}

@Composable
fun UserItem(
    item: TelegramUser,
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
                text = item.telegramId,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                text = item.telegramMessage,
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