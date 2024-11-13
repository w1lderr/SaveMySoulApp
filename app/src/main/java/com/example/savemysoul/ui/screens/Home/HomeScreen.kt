package com.example.savemysoul.ui.screens.Home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.savemysoul.navigation.Screens
import com.google.android.gms.location.LocationServices

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val userList by viewModel.userList.observeAsState(emptyList())
    val uiState by viewModel.uiState.observeAsState(HomeUiState())
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    LaunchedEffect(uiState.toast) {
        uiState.toast.let {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.setToast("")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(140.dp)
        ) {
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

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(150.dp)
            ) {
                Button(
                    onClick = {
                        checkLocationAndSendSos(
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
                    )
                ) {
                    Text(
                        text = "SOS",
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {

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
        }
    }
}

private fun checkLocationAndSendSos(
    locationUtils: LocationUtils,
    context: Context,
    viewModel: HomeViewModel
) {
    locationUtils.getCurrentLocation(
        onLocationReceived = { location ->
            if (location != null) {
                viewModel.sendSOS("${location.latitude} ${location.longitude}")
            } else {
                viewModel.setToast("Не вдалося отримати вашу локацію :(")
            }
        },
        onPermissionDenied = {
            viewModel.setToast("Дайти доступ до вашої локації будь ласка :)")
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        },
        onLocationDisabled = {
            viewModel.setToast("Вімкніть локацію будь ласка :)")
        }
    )
}