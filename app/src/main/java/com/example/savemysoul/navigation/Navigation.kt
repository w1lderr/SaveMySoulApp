package com.example.savemysoul.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.savemysoul.ui.screens.AddUser.AddUserScreen
import com.example.savemysoul.ui.screens.Home.HomeScreen
import com.example.savemysoul.ui.screens.Info.InfoScreen
import com.example.savemysoul.ui.screens.ShowUsers.ShowUsersScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(Screens.InfoScreen.name) {
            InfoScreen(navController)
        }
        composable(Screens.AddUserScreen.name) {
            AddUserScreen(navController)
        }
        composable(Screens.ShowUsersScreen.name) {
            ShowUsersScreen(navController)
        }
    }
}