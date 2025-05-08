package com.application.auth

import com.common.global.auth.role.Role
import com.common.global.auth.token.TokenProvider
import com.common.global.exceptions.base.CustomException
import com.common.util.throwWhen
import com.domain.auth.Auth
import com.domain.auth.exception.AuthExceptionType
import com.domain.auth.port.`in`.AuthUseCase
import com.domain.auth.port.`in`.command.SignInCommand
import com.domain.auth.port.`in`.command.SignUpCommand
import com.domain.auth.port.out.AuthPasswordEncryptor
import com.domain.auth.port.out.AuthRepositoryPort
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepositoryPort: AuthRepositoryPort,
    private val authPasswordEncryptor: AuthPasswordEncryptor,
    private val tokenProvider: TokenProvider,
) : AuthUseCase {

    override fun signUp(command: SignUpCommand): String {
        throwWhen(authRepositoryPort.existsByUsername(command.username)) {
            CustomException(AuthExceptionType.ALREADY_EXISTS_USERNAME)
        }

        val role = Role.findByName(command.role)

        val savedAuth =
            authRepositoryPort.save(
                Auth.signUpWithEncryption(
                    username = command.username,
                    password = command.password,
                    authPasswordEncryptor = authPasswordEncryptor,
                    role = role,
                ),
            )

        return tokenProvider.create(savedAuth.id, role)
    }

    override fun signIn(command: SignInCommand): String {
        val auth =
            authRepositoryPort.findByUsername(command.username)
                ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND)

        if (!auth.matches(command.password, authPasswordEncryptor)) {
            throw CustomException(AuthExceptionType.INVALID_PASSWORD)
        }

        return tokenProvider.create(auth.id, auth.role)
    }
}
