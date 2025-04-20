package com.adapter.auth

import com.domain.auth.port.out.AuthPasswordEncryptorPort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurityPasswordEncryptAdapter(
    private val passwordEncoder: PasswordEncoder
) : AuthPasswordEncryptorPort {

    override fun encrypt(password: String): String {
        return passwordEncoder.encode(password)
    }

    override fun matches(password: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(password, encodedPassword)
    }
}
