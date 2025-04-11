package com.example.habittracker.features.auth.domain.use_case


import com.example.habittracker.core.util.SimpleResult
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    /**
     * Logs out the currently authenticated user.
     *
     * @return A [SimpleResult] indicating success or failure (with an Exception).
     */
    suspend operator fun invoke(): SimpleResult {
        // Potential place for pre-logout logic (e.g., clearing specific user caches)
        // if it's considered domain logic.

        // Delegate the actual logout operation to the repository
        return repository.logout()
    }
}