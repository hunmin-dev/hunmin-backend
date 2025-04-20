package com.domain.auth.service

import com.domain.auth.port.out.AuthPasswordEncryptorPort

class StubAuthPasswordEncryptorAdaptor : AuthPasswordEncryptorPort {
    override fun encrypt(password: String): String = "encrypted:$password"

    override fun matches(
        password: String,
        encodedPassword: String,
    ): Boolean = encodedPassword == encrypt(password)
}
