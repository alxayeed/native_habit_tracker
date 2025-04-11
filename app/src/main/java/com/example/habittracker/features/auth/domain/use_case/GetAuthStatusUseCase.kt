package com.example.habittracker.features.auth.domain.use_case

import com.example.habittracker.features.auth.domain.entity.AuthStatus
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthStatus> {
        // Directly exposes the flow from the repository
        return repository.getSessionStatus()
    }
}