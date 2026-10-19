package com.entregafood.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.entregafood.app.data.AuthRepository
import com.entregafood.app.data.MockAuthRepository
import com.entregafood.app.data.Session
import com.entregafood.app.data.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface SessionState {
    data object Loading : SessionState
    data object LoggedOut : SessionState
    data class LoggedIn(val session: Session) : SessionState
}

data class LoginUiState(val loading: Boolean = false, val error: String? = null)

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val storage = SessionStorage(app)
    private val repository: AuthRepository = MockAuthRepository() // trocar pela API real depois

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    private val _login = MutableStateFlow(LoginUiState())
    val login: StateFlow<LoginUiState> = _login.asStateFlow()

    init {
        // Restaura a sessão salva (fora da thread principal: usa o Keystore).
        viewModelScope.launch {
            val saved = withContext(Dispatchers.IO) { storage.load() }
            _sessionState.value = saved?.let { SessionState.LoggedIn(it) } ?: SessionState.LoggedOut
        }
    }

    fun signIn(email: String, password: String) {
        if (_login.value.loading) return
        _login.value = LoginUiState(loading = true)
        viewModelScope.launch {
            try {
                val session = repository.login(email, password)
                withContext(Dispatchers.IO) { storage.save(session) }
                _login.value = LoginUiState()
                _sessionState.value = SessionState.LoggedIn(session)
            } catch (e: Exception) {
                _login.update {
                    LoginUiState(error = e.message ?: "Não foi possível entrar. Tente novamente.")
                }
            }
        }
    }

    fun clearLoginError() = _login.update { it.copy(error = null) }

    fun signOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { storage.clear() }
            _sessionState.value = SessionState.LoggedOut
        }
    }
}
