package com.domain.auth

import com.common.global.auth.role.Role
import com.domain.aggregate.AggregateRoot
import com.domain.auth.event.AuthEvent
import com.domain.auth.port.out.AuthPasswordEncryptor

class Auth(
    override val id: Long = 0,
    val username: String,
    val password: String,
    val role: Role = Role.USER,
) : AggregateRoot<AuthEvent, Long>() {

    fun matches(
        password: String,
        authPasswordEncryptor: AuthPasswordEncryptor,
    ): Boolean = authPasswordEncryptor.matches(password, this.password)

    companion object {
        fun signUpWithEncryption(
            username: String,
            password: String,
            authPasswordEncryptor: AuthPasswordEncryptor,
            role: Role,
        ) =
            Auth(
                username = username,
                password = authPasswordEncryptor.encrypt(password),
                role = role
            ).apply {
                AuthEvent.created(this)
            }

        fun anonymousAuth() =
            Auth(
                id = -1L,
                username = "",
                password = "",
                role = Role.ANONYMOUS
            ).apply {
                AuthEvent.created(this)
            }
    }
}
