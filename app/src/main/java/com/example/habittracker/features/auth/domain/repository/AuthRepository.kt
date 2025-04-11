package com.example.habittracker.features.auth.domain.repository

import com.example.habittracker.core.domain.model.User
import com.example.habittracker.core.util.SimpleResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * Provides a continuous Flow of the current authentication status.
     */
    fun getSessionStatus(): Flow<AuthStatus>

    /**
     * Gets the currently authenticated user details, if available.
     * Can be null if not authenticated or if details aren't loaded yet.
     */
    suspend fun getCurrentUser(): User?

    /**
     * Attempts to sign up a new user.
     * Returns Result.success(Unit) on success, Result.failure(exception) on error.
     */
    suspend fun signUp(email: String, pass: String): SimpleResult

    /**
     * Attempts to log in a user.
     * Returns Result.success(Unit) on success, Result.failure(exception) on error.
     */
    suspend fun login(email: String, pass: String): SimpleResult

    /**
     * Logs out the current user.
     * Returns Result.success(Unit) on success, Result.failure(exception) on error.
     */
    suspend fun logout(): SimpleResult

    /**
     * Sends a password reset email to the given address.
     * Returns Result.success(Unit) on success, Result.failure(exception) on error.
     */
    suspend fun sendPasswordReset(email: String): SimpleResult
}