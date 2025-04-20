package com.domain.auth.port.out

interface TokenProviderPort {
    fun create(id: Long): String

    fun extract(token: String): Long
}
