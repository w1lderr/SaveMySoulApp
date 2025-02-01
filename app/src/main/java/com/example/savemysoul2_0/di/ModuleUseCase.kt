package com.example.savemysoul2_0.di

import com.example.savemysoul2_0.data.repo.TelegramUserRepo
import com.example.savemysoul2_0.data.repo.TelegramUserServiceRepo
import com.example.savemysoul2_0.domain.useCase.AddUserScreenUseCase
import com.example.savemysoul2_0.domain.useCase.HomeScreenUseCase
import com.example.savemysoul2_0.domain.useCase.ShowUsersScreenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ModuleUseCase {
    @Provides
    fun provideAddScreenUseCase(telegramUserRepo: TelegramUserRepo): AddUserScreenUseCase {
        return AddUserScreenUseCase(telegramUserRepo)
    }

    @Provides
    fun provideShowUsersScreenUseCase(telegramUserRepo: TelegramUserRepo): ShowUsersScreenUseCase {
        return ShowUsersScreenUseCase(telegramUserRepo)
    }

    @Provides
    fun provideHomeScreenUseCase(telegramUserServiceRepo: TelegramUserServiceRepo): HomeScreenUseCase {
        return HomeScreenUseCase(telegramUserServiceRepo)
    }
}