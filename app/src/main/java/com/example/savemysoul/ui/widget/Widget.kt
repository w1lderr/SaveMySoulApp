package com.example.savemysoul.ui.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.example.savemysoul.MainActivity
import com.example.savemysoul.R
import com.example.savemysoul.ui.screens.Home.HomeViewModel
import com.example.savemysoul.ui.screens.Home.LocationUtils
import com.example.savemysoul.ui.screens.Home.checkLocationAndSendSos

class Widget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val homeViewModel = HomeViewModel()
        val locationUtils = LocationUtils(context)

        provideContent {
            MyWidget(homeViewModel, locationUtils, context)
        }
    }

    @Composable
    private fun MyWidget(
        viewModel: HomeViewModel,
        locationUtils: LocationUtils,
        context: Context
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        Box(
            modifier = GlanceModifier
                .size(100.dp)
                .cornerRadius(90.dp)
                .background(color = Color.Black)
                .clickable {
                    context.startActivity(intent)
                    checkLocationAndSendSos(locationUtils, context, viewModel)
                }
        ) {
            Image(
                provider = ImageProvider(R.drawable.logo),
                contentDescription = "Logo",
                modifier = GlanceModifier.padding(15.dp)
            )
        }
    }
}