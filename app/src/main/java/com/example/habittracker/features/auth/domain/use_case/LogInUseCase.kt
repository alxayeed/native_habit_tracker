package com.example.habittracker.features.auth.domain.use_case
import android.util.Patterns
import com.example.habittracker.core.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    /**
     * Attempts to log in a user with the provided email and password.
     * Performs basic validation before calling the repository.
     *
     * @param email The user's email address.
     * @param pass The user's password.
     * @return A [SimpleResult] indicating success or failure (with an Exception).
     */
    suspend operator fun invoke(email: String, pass: String): SimpleResult {
        // Basic input validation (could be expanded)
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email cannot be blank."))
        }
        if (pass.isBlank()) {
            return Result.failure(IllegalArgumentException("Password cannot be blank."))
        }
        // Basic email format check (optional, depends if you want domain vs UI validation)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format."))
        }

        // Delegate the actual login operation to the repository
        return repository.login(email, pass)
    }
}