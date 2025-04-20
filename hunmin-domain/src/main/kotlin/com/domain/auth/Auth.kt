package com.domain.auth

import com.domain.auth.port.out.AuthPasswordEncryptorPort

class Auth(
    val id: Long = 0,
    val username: String,
    val password: String,
) {

    fun matches(password: String, authPasswordEncryptorPort: AuthPasswordEncryptorPort): Boolean {
        return authPasswordEncryptorPort.matches(password, this.password)
    }

    companion object {
        fun signUpWithEncryption(
            username: String,
            password: String,
            authPasswordEncryptorPort: AuthPasswordEncryptorPort
        ) = Auth(username = username, password = authPasswordEncryptorPort.encrypt(password))
    }
}
