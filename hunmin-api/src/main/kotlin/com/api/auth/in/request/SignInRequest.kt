package com.api.auth.`in`.request

import com.application.auth.port.`in`.command.SignInCommand
import io.swagger.v3.oas.annotations.media.Schema

data class SignInRequest(
    @Schema(
        description = "로그인 id",
        example = "root",
    )
    val username: String,

    @Schema(
        description = "로그인 패스워드",
        example = "root",
    )
    val password: String
) {

    fun toCommand() = SignInCommand(
        username = username,
        password = password
    )
}
