package com.example.savemysoul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.savemysoul.navigation.Navigation
import com.example.savemysoul.ui.theme.SaveMySoulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SaveMySoulTheme {
                Navigation(navController)
            }
        }
    }
}