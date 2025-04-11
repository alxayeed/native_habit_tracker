package com.example.habittracker.features.auth.domain.use_case

import android.util.Patterns
import com.example.habittracker.core.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

// Renamed class
class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    /**
     * Initiates the password reset process for the specified email address.
     * Performs basic validation on the email format.
     *
     * @param email The email address to send the reset link to.
     * @return A [SimpleResult] indicating success or failure (with an Exception).
     */
    suspend operator fun invoke(email: String): SimpleResult {
        // Basic input validation
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email cannot be blank."))
        }
        // Basic email format check
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format."))
        }

        // Delegate the actual password reset request to the repository
        // Repository method might still be named sendPasswordReset or similar
        return repository.sendPasswordReset(email)
    }
}