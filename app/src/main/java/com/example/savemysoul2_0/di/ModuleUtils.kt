package com.example.savemysoul2_0.di

import android.content.Context
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator
import com.example.savemysoul2_0.ui.screens.Home.LocationUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ModuleUtils {
    @Provides
    fun provideAndroidUuidGenerator(@ApplicationContext context: Context): AndroidUuidGenerator {
        return AndroidUuidGenerator(context)
    }

    @Provides
    fun provideLocationUtils(@ApplicationContext context: Context): LocationUtils {
        return LocationUtils(context)
    }
}