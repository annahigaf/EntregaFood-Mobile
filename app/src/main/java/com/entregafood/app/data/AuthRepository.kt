package com.entregafood.app.data

import kotlinx.coroutines.delay

interface AuthRepository {
    /** Lança Exception com mensagem amigável em caso de falha. */
    suspend fun login(email: String, password: String): Session
}

/**
 * MOCK: aceita apenas cliente@entregafood.com / 123456.
 * Quando o backend existir, crie uma implementação com Retrofit
 * (POST /auth/login) e troque no AuthViewModel.
 */
class MockAuthRepository : AuthRepository {
    override suspend fun login(email: String, password: String): Session {
        delay(800)
        if (email.equals("cliente@entregafood.com", ignoreCase = true) && password == "123456") {
            return Session("mock-jwt-token", User("1", "Cliente", email))
        }
        throw IllegalArgumentException("E-mail ou senha incorretos.")
    }
}
