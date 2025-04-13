package com.example.habittracker.features.auth.data.datasources // Make sure this matches your actual package structure

import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo as SupabaseUser // Alias for clarity
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject // Import for JsonObject

interface AuthRemoteDataSource {

    fun observeSessionStatus(): Flow<SessionStatus>

    suspend fun retrieveCurrentUser(): SupabaseUser?

    suspend fun signUpWithEmail(email: String, pass: String, data: JsonObject? = null)

    suspend fun loginWithEmail(email: String, pass: String)

    suspend fun logout()

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun updateUserProfile(
        newPassword: String? = null,
        newEmail: String? = null,
        data: JsonObject? = null
    )
}