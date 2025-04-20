package com.domain.auth.port.`in`

import com.domain.auth.port.`in`.command.SignInCommand
import com.domain.auth.port.`in`.command.SignUpCommand

interface AuthUseCase {

    fun signUp(command: SignUpCommand): String

    fun signIn(command: SignInCommand): String
}
