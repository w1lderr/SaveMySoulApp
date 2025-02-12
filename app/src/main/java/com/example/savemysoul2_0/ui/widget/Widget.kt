package com.example.savemysoul2_0.ui.widget

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.example.savemysoul2_0.MainActivity
import com.example.savemysoul2_0.R
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator
import com.example.savemysoul2_0.ui.screens.Home.HomeViewModel
import com.example.savemysoul2_0.ui.screens.Home.LocationUtils
import com.example.savemysoul2_0.ui.screens.Home.requestLocationAndSendSOS
import javax.inject.Inject


class Widget : GlanceAppWidget() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var locationUtils: LocationUtils

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyWidget(homeViewModel, locationUtils, context)
        }
    }

    @Composable
    private fun MyWidget(
        viewModel: HomeViewModel,
        locationUtils: LocationUtils,
        context: Context,
    ) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(20.dp)
                .background(color = Color.Black)
                .clickable {
                    requestLocationAndSendSOS(locationUtils, context, viewModel)
                }
        ) {
            Image(
                provider = ImageProvider(R.drawable.logo),
                contentDescription = "Logo",
                modifier = GlanceModifier.padding(10.dp)
            )

        }
    }
}