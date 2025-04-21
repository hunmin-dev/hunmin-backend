package com.domain.auth

import com.domain.auth.port.out.AuthPasswordEncryptor

class Auth(
    val id: Long = 0,
    val username: String,
    val password: String,
) {
    fun matches(
        password: String,
        authPasswordEncryptor: AuthPasswordEncryptor,
    ): Boolean = authPasswordEncryptor.matches(password, this.password)

    companion object {
        fun signUpWithEncryption(
            username: String,
            password: String,
            authPasswordEncryptor: AuthPasswordEncryptor,
        ) = Auth(username = username, password = authPasswordEncryptor.encrypt(password))
    }
}
