package com.example.habittracker.features.auth.data.datasources // Make sure this matches your actual package structure

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo as SupabaseUser // Alias UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthRemoteDataSource {

    // Convenience getter for the Auth plugin
    private val auth: Auth get() = supabaseClient.auth

    /**
     * Provides a flow of the underlying Supabase session status.
     */
    override fun observeSessionStatus(): Flow<SessionStatus> {
        return auth.sessionStatus
    }

    /**
     * Retrieves the full Supabase UserInfo object for the currently active session.
     * Returns null if not authenticated.
     */
    override suspend fun retrieveCurrentUser(): SupabaseUser? {
        // Access the UserInfo object (which is the 'user' property of the session)
        return auth.currentSessionOrNull()?.user
    }

    /**
     * Attempts to sign up a new user via Supabase Auth.
     * Throws exceptions on failure.
     * @param email User's email.
     * @param pass User's password.
     * @param data Optional user metadata to set during signup.
     */
    override suspend fun signUpWithEmail(email: String, pass: String, data: JsonObject?) {
        // Supabase Auth throws specific exceptions on failure
        auth.signUpWith(Email) {
            this.email = email
            this.password = pass
            // Pass user metadata if provided
            this.data = data
        }
    }

    /**
     * Attempts to log in a user via Supabase Auth.
     * Throws exceptions on failure.
     */
    override suspend fun loginWithEmail(email: String, pass: String) {
        auth.signInWith(Email) {
            this.email = email
            this.password = pass
        }
    }

    /**
     * Logs out the current user from Supabase Auth.
     * Throws exceptions on failure.
     */
    override suspend fun logout() {
        auth.signOut()
    }

    /**
     * Sends a password reset email via Supabase Auth.
     * Throws exceptions on failure.
     */
    override suspend fun sendPasswordResetEmail(email: String) {
        auth.resetPasswordForEmail(email)
        // Optional: Add redirect URL if needed for your email template setup
        // auth.resetPasswordForEmail(email, redirectUrl = "your-app://reset-callback")
    }

    /**
     * Updates attributes for the currently authenticated user.
     * Null parameters mean the attribute will not be updated.
     * Throws exceptions on failure.
     * @param newPassword Optional new password.
     * @param newEmail Optional new email (requires verification).
     * @param data Optional updated user metadata (will overwrite existing metadata).
     */
    override suspend fun updateUserProfile(
        newPassword: String?,
        newEmail: String?,
        data: JsonObject?
    ) {
        auth.updateUser {
            if (newPassword != null) {
                this.password = newPassword
            }
            if (newEmail != null) {
                this.email = newEmail
                // Optional: Add redirect URL for email change verification if needed
                // this.emailRedirectTo = "your-app://update-email-callback"
            }
            if (data != null) {
                this.data = data
            }
        }
    }
}