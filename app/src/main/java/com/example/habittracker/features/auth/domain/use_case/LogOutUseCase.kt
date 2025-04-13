package com.example.habittracker.features.auth.domain.use_case

import com.example.habittracker.core.domain.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResult {
        return repository.logout()
    }
}