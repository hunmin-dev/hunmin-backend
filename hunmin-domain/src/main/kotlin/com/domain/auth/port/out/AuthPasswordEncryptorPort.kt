package com.domain.auth.port.out

interface AuthPasswordEncryptorPort {
    fun encrypt(password: String): String

    fun matches(
        password: String,
        encodedPassword: String,
    ): Boolean
}
