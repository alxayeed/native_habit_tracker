package com.example.habittracker.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.features.auth.domain.entity.AuthStatus

import com.example.habittracker.features.auth.domain.use_case.GetAuthStatusUseCase
import com.example.habittracker.features.auth.domain.use_case.LoginUseCase
import com.example.habittracker.features.auth.domain.use_case.LogoutUseCase
import com.example.habittracker.features.auth.domain.use_case.ResetPasswordUseCase
import com.example.habittracker.features.auth.domain.use_case.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthScreenState(
    val authStatus: AuthStatus = AuthStatus.Loading,
    val isLoading: Boolean = false,
    val error: String? = null,
    val resetEmailSent: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthScreenState())
    val uiState: StateFlow<AuthScreenState> = _uiState.asStateFlow()

    init {
        observeAuthStatus()
    }

    private fun observeAuthStatus() {
        getAuthStatusUseCase()
            .onEach { status ->
                _uiState.update { it.copy(authStatus = status) }
            }
            .launchIn(viewModelScope)
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = loginUseCase(email, pass)
            result.onFailure { throwable ->
                _uiState.update { it.copy(isLoading = false, error = throwable.message ?: "Login failed") }
            }
            result.onSuccess {
                if (_uiState.value.authStatus !is AuthStatus.Authenticated) {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun signUp(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = signUpUseCase(email, pass)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, resetEmailSent = false) }
            val result = resetPasswordUseCase(email)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message,
                    resetEmailSent = result.isSuccess
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = logoutUseCase()
            result.onFailure { throwable ->
                _uiState.update { it.copy(isLoading = false, error = throwable.message ?: "Logout failed") }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }

    fun resetPasswordSentFlag() {
        _uiState.update { it.copy(resetEmailSent = false) }
    }
}