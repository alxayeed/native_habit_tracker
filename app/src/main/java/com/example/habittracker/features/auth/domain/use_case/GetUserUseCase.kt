package com.example.habittracker.features.auth.domain.use_case

import com.example.habittracker.core.domain.model.User
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        // Could add extra logic here if needed, e.g., fetching more profile details
        return repository.getCurrentUser()
    }
}
