package com.example.habittracker.features.auth.domain.use_case

import android.util.Patterns
import com.example.habittracker.core.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String): SimpleResult {
        // Example validation: Check password strength, email format etc.
        if (pass.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters long."))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format."))
        }
        // More complex business logic could reside here
        return repository.signUp(email, pass)
    }
}