package com.example.habittracker.features.auth.domain.entity

import com.example.habittracker.core.domain.model.User

sealed class AuthStatus {
    data object Loading : AuthStatus() // Initial state or during auth operations
    data class Authenticated(val user: User) : AuthStatus() // User is logged in
    data object Unauthenticated : AuthStatus() // User is not logged in or logged out
    // You could add an Error state here too if needed for the status flow
    // data class Error(val message: String) : AuthStatus()
}