package com.example.savemysoul2_0.ui.screens.Home

import android.content.Context
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.savemysoul2_0.navigation.Screens
import android.content.Intent
import android.provider.Settings
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState(HomeUiState())
    val context = LocalContext.current
    val androidUuidGenerator = AndroidUuidGenerator.getOrCreateGuid(context)
    val locationUtils = LocationUtils(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Save My Soul",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                Icons.Default.Info,
                contentDescription = "Info",
                tint = Color.White,
                modifier = Modifier
                    .size(width = 35.dp, height = 35.dp)
                    .clickable {
                        navController.navigate(Screens.InfoScreen.name)
                    }
            )
        }

        Spacer(modifier = Modifier.height(150.dp))

        Button(
            onClick = {
                requestLocationAndSendSOS(
                    uuid = androidUuidGenerator,
                    locationUtils = locationUtils,
                    context = context,
                    viewModel = viewModel
                )
            },
            modifier = Modifier.size(width = 270.dp, height = 120.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            enabled = !uiState.value.showProgress
        ) {
            Text(
                text = "SOS",
                fontSize = 80.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        if (uiState.value.showProgress == true) {
            Text(
                text = "SOS надіслано!",
                fontSize = 20.sp,
                color = Color.White
            )
        } else {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                navController.navigate(Screens.AddUserScreen.name)
            },
            modifier = Modifier
                .size(width = 270.dp, height = 62.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Додати Користувача",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate(Screens.ShowUsersScreen.name)
            },
            modifier = Modifier
                .size(width = 270.dp, height = 62.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Подивитись Користувачів",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

fun requestLocationAndSendSOS(
    uuid: String,
    locationUtils: LocationUtils,
    context: Context,
    viewModel: HomeViewModel
) {
    locationUtils.getCurrentLocation(
        onLocationReceived = { location ->
            if (location != null) {
                viewModel.sendSOS(uuid, "<a href=\"https://www.google.com/maps?q=${location.latitude},${location.longitude}\"><b>Моя локація</b></a>", context)
            } else {
                Toast.makeText(context, "Не вдалося отримати вашу локацію :(", Toast.LENGTH_SHORT).show()
            }
        },
        onPermissionDenied = {
            Toast.makeText(context, "Дайте доступ до вашої локації будь ласка :)", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        },
        onLocationDisabled = {
            Toast.makeText(context, "Увімкніть локацію будь ласка :)", Toast.LENGTH_SHORT).show()
        }
    )
}