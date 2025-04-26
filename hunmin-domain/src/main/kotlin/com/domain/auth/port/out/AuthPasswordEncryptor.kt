package com.domain.auth.port.out

interface AuthPasswordEncryptor {
    fun encrypt(password: String): String

    fun matches(
        password: String,
        encodedPassword: String,
    ): Boolean
}
