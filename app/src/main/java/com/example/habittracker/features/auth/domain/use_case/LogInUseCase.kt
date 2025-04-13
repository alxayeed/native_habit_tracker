package com.example.habittracker.features.auth.domain.use_case

import android.util.Patterns
import com.example.habittracker.core.domain.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String): SimpleResult {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email cannot be blank."))
        }
        if (pass.isBlank()) {
            return Result.failure(IllegalArgumentException("Password cannot be blank."))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format."))
        }
        return repository.login(email, pass)
    }
}