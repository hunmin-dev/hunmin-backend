package com.application.auth.port.`in`

import com.application.auth.port.`in`.command.SignInCommand
import com.application.auth.port.`in`.command.SignUpCommand

interface AuthUseCase {

    fun signUp(command: SignUpCommand): String

    fun signIn(command: SignInCommand): String
}
