package com.application.auth

import com.application.auth.port.`in`.command.SignInCommand
import com.application.auth.port.`in`.command.SignUpCommand
import com.domain.auth.Auth

class AuthCommandFixture {
    companion object {
        fun 인증_생성(): Auth =
            Auth(
                username = "username",
                password = "password",
            )

        fun 인증_생성_커맨드(): SignUpCommand =
            SignUpCommand(
                username = "username",
                password = "password",
            )

        fun 인증_로그인_커맨드(): SignInCommand =
            SignInCommand(
                username = "username",
                password = "password",
            )
    }
}
