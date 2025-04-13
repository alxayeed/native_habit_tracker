package com.example.habittracker.features.auth.domain.use_case

import com.example.habittracker.core.domain.entity.User
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}