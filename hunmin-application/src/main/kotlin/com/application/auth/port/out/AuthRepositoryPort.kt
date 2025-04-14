package com.application.auth.port.out

import com.domain.auth.Auth

interface AuthRepositoryPort {

    fun save(auth: Auth): Auth

    fun findByUsername(username: String): Auth?

    fun existsByUsername(username: String): Boolean
}
