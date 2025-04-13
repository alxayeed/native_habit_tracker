package com.example.habittracker.features.auth.data.repository

import com.example.habittracker.features.auth.data.datasources.AuthRemoteDataSource
import com.example.habittracker.features.auth.domain.entity.AuthStatus
import com.example.habittracker.features.auth.domain.repository.AuthRepository
import com.example.habittracker.core.domain.entity.User // Ensure this path is correct
import com.example.habittracker.core.domain.util.SimpleResult
import com.example.habittracker.core.domain.util.runOperationCatching
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo as SupabaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override fun getSessionStatus(): Flow<AuthStatus> {
        return remoteDataSource.observeSessionStatus()
            .map { supabaseStatus ->
                when (supabaseStatus) {
                    is SessionStatus.Authenticated -> {
                        val supabaseUser: SupabaseUser? = supabaseStatus.session.user
                        val domainUser = User(
                            id = supabaseUser?.id ?: "",
                            email = supabaseUser?.email
                        )
                        if (domainUser.id.isBlank()) {
                            AuthStatus.Unauthenticated
                        } else {
                            AuthStatus.Authenticated(domainUser)
                        }
                    }
                    is SessionStatus.LoadingFromStorage -> {
                        AuthStatus.Loading
                    }
                    is SessionStatus.NetworkError -> {
                        println("Session Status Network Error: $supabaseStatus")
                        AuthStatus.Unauthenticated
                    }
                    is SessionStatus.NotAuthenticated -> {
                        AuthStatus.Unauthenticated
                    }
                }
            }
            .catch { e ->
                println("Error collecting session status flow: ${e.message}")
                emit(AuthStatus.Unauthenticated)
            }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            val supabaseUser: SupabaseUser? = remoteDataSource.retrieveCurrentUser()
            supabaseUser?.let {
                User(
                    id = it.id,
                    email = it.email
                )
            }
        } catch (e: Exception) {
            println("Error retrieving current user: ${e.message}")
            null
        }
    }

    override suspend fun signUp(email: String, pass: String): SimpleResult {
        return runOperationCatching { remoteDataSource.signUpWithEmail(email, pass, null) }
    }

    override suspend fun login(email: String, pass: String): SimpleResult {
        return runOperationCatching { remoteDataSource.loginWithEmail(email, pass) }
    }

    override suspend fun logout(): SimpleResult {
        return runOperationCatching { remoteDataSource.logout() }
    }

    override suspend fun sendPasswordReset(email: String): SimpleResult {
        return runOperationCatching { remoteDataSource.sendPasswordResetEmail(email) }
    }
}