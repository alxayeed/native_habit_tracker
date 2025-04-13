package com.example.habittracker.features.auth.domain.use_case

import android.util.Patterns
import com.example.habittracker.core.domain.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): SimpleResult {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email cannot be blank."))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format."))
        }
        return repository.sendPasswordReset(email)
    }
}