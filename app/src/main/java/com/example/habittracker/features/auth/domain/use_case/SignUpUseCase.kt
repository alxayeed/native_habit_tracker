package com.example.habittracker.features.auth.domain.use_case

import android.util.Patterns // Note: Domain layer ideally shouldn't have Android framework dependencies
import com.example.habittracker.core.domain.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String): SimpleResult {
        if (pass.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters long."))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format."))
        }
        return repository.signUp(email, pass)
    }
}