package com.domain.auth.service

class StubAuthPasswordEncryptorAdaptor : AuthPasswordEncryptorPort {

    override fun encrypt(password: String): String {
        return "encrypted:$password"
    }

    override fun matches(password: String, encodedPassword: String): Boolean {
        return encodedPassword == encrypt(password)
    }
}
