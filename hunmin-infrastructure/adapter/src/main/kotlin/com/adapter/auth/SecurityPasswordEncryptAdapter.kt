package com.adapter.auth

import com.domain.auth.port.out.AuthPasswordEncryptor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurityPasswordEncryptAdapter(
    private val passwordEncoder: PasswordEncoder,
) : AuthPasswordEncryptor {
    override fun encrypt(password: String): String = passwordEncoder.encode(password)

    override fun matches(
        password: String,
        encodedPassword: String,
    ): Boolean = passwordEncoder.matches(password, encodedPassword)
}
