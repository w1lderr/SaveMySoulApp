package com.example.savemysoul2_0.ui.screens.AddUser

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator
import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(navController: NavController, viewModel: AddUserViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState(AddUserUiState())
    val telegramUser = viewModel.telegramUser.collectAsState(TelegramUser())
    val context = LocalContext.current
    val androidUuidGenerator = AndroidUuidGenerator.getOrCreateGuid(context)

    LaunchedEffect(uiState.value.toast) {
        if (uiState.value.toast.isNotEmpty()) {
            Toast.makeText(context, uiState.value.toast, Toast.LENGTH_SHORT).show()
            viewModel.setToast("")
        }
    }

    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess == true) {
            navController.navigate(Screens.HomeScreen.name)
            viewModel.setIsSuccess(false)
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
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier
                    .size(width = 35.dp, height = 35.dp)
                    .clickable {
                        // Why am I not using popBackStack here? Because this option is more reliable
                        navController.navigate(Screens.HomeScreen.name)
                    }
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = telegramUser.value.telegramId,
                onValueChange = { viewModel.onTelegramIdChanged(it) },
                label = { Text("Телеграм айді") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = telegramUser.value.telegramMessage,
                onValueChange = { viewModel.onTelegramMessageChanged(it) },
                label = { Text("Повідомлення") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button( onClick = {
                viewModel.addTelegramUser(androidUuidGenerator)
            },
                modifier = Modifier.size(width = 280.dp, height = 60.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Додати",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}