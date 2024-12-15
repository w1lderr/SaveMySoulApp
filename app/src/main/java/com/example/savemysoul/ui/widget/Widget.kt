package com.example.savemysoul.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.height
import androidx.glance.layout.width
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
        Button(
            text = "SOS",
            onClick = {
                checkLocationAndSendSos(locationUtils, context, viewModel)
            },
            modifier = GlanceModifier
                .height(50.dp)
                .width(50.dp)
        )
    }
}