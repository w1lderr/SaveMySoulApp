package com.example.savemysoul2_0.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.savemysoul2_0.ui.screens.AddUser.AddUserScreen
import com.example.savemysoul2_0.ui.screens.Home.HomeScreen
import com.example.savemysoul2_0.ui.screens.Settings.SettingsScreen
import com.example.savemysoul2_0.ui.screens.ShowUsersScreen.ShowUsersScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(Screens.InfoScreen.name) {
            SettingsScreen(navController)
        }
        composable(Screens.AddUserScreen.name) {
            AddUserScreen(navController)
        }
        composable(Screens.ShowUsersScreen.name) {
            ShowUsersScreen(navController)
        }
    }
}