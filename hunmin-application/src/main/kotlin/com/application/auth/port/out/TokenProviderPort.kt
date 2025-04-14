package com.application.auth.port.out

interface TokenProviderPort {

    fun create(id: Long): String

    fun extract(token: String): Long
}
