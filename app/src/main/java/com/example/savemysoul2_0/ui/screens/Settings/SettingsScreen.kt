package com.example.savemysoul2_0.ui.screens.Settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.savemysoul2_0.navigation.Screens
import androidx.core.net.toUri

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current

    val linkToSourceCode = buildAnnotatedString {
        withStyle(style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
            textDecoration = TextDecoration.Underline,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )) {
            pushStringAnnotation(tag = "URL", annotation = "https://github.com/w1lderr/SaveMySoulApp")
            append("Код проекту Save My Soul")
            pop()
        }
    }

    val linkHowToUseIt = buildAnnotatedString {
        withStyle(style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
            textDecoration = TextDecoration.Underline,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )) {
            pushStringAnnotation(tag = "URL", annotation = "https://save-my-soul-site-instruction.vercel.app")
            append("Як цим користуватись?")
            pop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Icon(
            Icons.Default.Close,
            contentDescription = "Close",
            tint = Color.White,
            modifier = Modifier
                .size(35.dp)
                .clickable {
                    // Why i'm not using popBackStack here? Because this option is more reliable
                    navController.navigate(Screens.HomeScreen.name)
                }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = linkToSourceCode,
            fontSize = 22.sp,
            color = Color.White,
            modifier = Modifier.clickable {
                linkToSourceCode.getStringAnnotations(tag = "URL", start = 0, end = linkToSourceCode.length)
                    .firstOrNull()?.let { annotation ->
                        val intent = Intent(Intent.ACTION_VIEW, annotation.item.toUri())
                        context.startActivity(intent)
                    }
            }
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = linkHowToUseIt,
            fontSize = 22.sp,
            color = Color.White,
            modifier = Modifier.clickable {
                linkHowToUseIt.getStringAnnotations(tag = "URL", start = 0, end = linkHowToUseIt.length)
                    .firstOrNull()?.let { annotation ->
                        val intent = Intent(Intent.ACTION_VIEW, annotation.item.toUri())
                        context.startActivity(intent)
                    }
            }
        )
    }
}