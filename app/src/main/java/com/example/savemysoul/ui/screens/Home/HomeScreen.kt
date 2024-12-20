package com.example.savemysoul.ui.screens.Home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceTheme.colors
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.savemysoul.navigation.Screens

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState(HomeUiState())
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    Column(
        modifier = Modifier.fillMaxSize(),
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
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 23.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                Icons.Default.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.onBackground,
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
                    locationUtils,
                    context,
                    viewModel
                )
            },
            modifier = Modifier.size(width = 270.dp, height = 120.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            enabled = !uiState.showProgressBar
        ) {
            Text(
                text = "SOS",
                fontSize = 80.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        if (uiState.showProgressBar) {
            LinearProgressIndicator(
                progress = { uiState.progressBar },
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
                    .width(220.dp)
                    .height(10.dp),
                color = MaterialTheme.colorScheme.onBackground,
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
                .size(width = 270.dp, height = 60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = Color.White
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
                .size(width = 270.dp, height = 60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = Color.White
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
    locationUtils: LocationUtils,
    context: Context,
    viewModel: HomeViewModel
) {
    locationUtils.getCurrentLocation(
        onLocationReceived = { location ->
            if (location != null) {
                viewModel.sendSOS(location.latitude, location.longitude, context)
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
