package com.example.savemysoul2_0.di

import com.example.savemysoul2_0.data.repo.TelegramUserRepo
import com.example.savemysoul2_0.data.repo.TelegramUserServiceRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleRepo {
    @Provides
    @Singleton
    fun provideTelegramUserRepo(): TelegramUserRepo {
        return TelegramUserRepo()
    }

    @Provides
    @Singleton
    fun provideTelegramUserServiceRepo(): TelegramUserServiceRepo {
        return TelegramUserServiceRepo()
    }
}