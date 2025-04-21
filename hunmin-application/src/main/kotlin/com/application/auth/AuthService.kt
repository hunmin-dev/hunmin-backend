package com.application.auth

import com.common.global.auth.token.TokenProvider
import com.common.global.exceptions.base.CustomException
import com.common.util.throwWhen
import com.domain.auth.Auth
import com.domain.auth.exception.AuthExceptionType
import com.domain.auth.port.`in`.AuthUseCase
import com.domain.auth.port.`in`.command.SignInCommand
import com.domain.auth.port.`in`.command.SignUpCommand
import com.domain.auth.port.out.AuthPasswordEncryptorPort
import com.domain.auth.port.out.AuthRepositoryPort
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepositoryPort: AuthRepositoryPort,
    private val authPasswordEncryptorPort: AuthPasswordEncryptorPort,
    private val tokenProvider: TokenProvider,
) : AuthUseCase {
    override fun signUp(command: SignUpCommand): String {
        throwWhen(authRepositoryPort.existsByUsername(command.username)) {
            CustomException(AuthExceptionType.USERNAME_ALREADY_EXISTS_EXCEPTION)
        }

        val savedAuth =
            authRepositoryPort.save(
                Auth.signUpWithEncryption(
                    username = command.username,
                    password = command.password,
                    authPasswordEncryptorPort = authPasswordEncryptorPort,
                ),
            )

        return tokenProvider.create(savedAuth.id)
    }

    override fun signIn(command: SignInCommand): String {
        val auth =
            authRepositoryPort.findByUsername(command.username)
                ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND_EXCEPTION)

        require(auth.matches(command.password, authPasswordEncryptorPort)) {
            AuthExceptionType.PASSWORD_INVALID_EXCEPTION
        }

        return tokenProvider.create(auth.id)
    }
}
