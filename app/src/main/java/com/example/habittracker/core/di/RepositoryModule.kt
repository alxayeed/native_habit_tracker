package com.example.habittracker.core.di // Ensure correct package

import com.example.habittracker.features.auth.data.repository.AuthRepositoryImpl
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import com.example.habittracker.features.auth.data.datasources.AuthRemoteDataSource
import com.example.habittracker.features.auth.data.datasources.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository


    // --- Add bindings for other repositories later ---
    // @Binds
    // @Singleton
    // abstract fun bindHabitRepository(
    //     habitRepositoryImpl: HabitRepositoryImpl
    // ): HabitRepository
    //
    // @Binds
    // @Singleton
    // abstract fun bindTrackingRepository(
    //     trackingRepositoryImpl: TrackingRepositoryImpl
    // ): TrackingRepository

}