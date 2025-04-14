package com.application.auth

import com.common.global.exceptions.base.CustomException
import com.common.util.throwWhen
import com.domain.auth.Auth
import com.domain.auth.service.AuthPasswordEncryptor
import com.application.auth.port.`in`.AuthUseCase
import com.application.auth.port.`in`.command.SignInCommand
import com.application.auth.port.`in`.command.SignUpCommand
import com.domain.auth.exception.AuthExceptionType
import com.application.auth.port.out.AuthRepositoryPort
import com.store.auth.application.port.out.TokenProviderPort
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepositoryPort: AuthRepositoryPort,
    private val authPasswordEncryptor: AuthPasswordEncryptor,
    private val tokenProviderPort: TokenProviderPort
) : AuthUseCase {

    override fun signUp(command: SignUpCommand): String {
        throwWhen(authRepositoryPort.existsByUsername(command.username)) {
            CustomException(AuthExceptionType.USERNAME_ALREADY_EXISTS_EXCEPTION)
        }

        val savedAuth = authRepositoryPort.save(
            Auth.signUpWithEncryption(
                username = command.username,
                password = command.password,
                authPasswordEncryptor = authPasswordEncryptor
            )
        )

        return tokenProviderPort.create(savedAuth.id)
    }

    override fun signIn(command: SignInCommand): String {
        val auth = authRepositoryPort.findByUsername(command.username)
            ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND_EXCEPTION)

        require(auth.matches(command.password, authPasswordEncryptor)) {
            AuthExceptionType.PASSWORD_INVALID_EXCEPTION
        }

        return tokenProviderPort.create(auth.id)
    }
}
