package com.domain.auth.port.out

class StubAuthPasswordEncryptorAdaptor : AuthPasswordEncryptor {
    override fun encrypt(password: String): String = "encrypted:$password"

    override fun matches(
        password: String,
        encodedPassword: String,
    ): Boolean = encodedPassword == encrypt(password)
}
