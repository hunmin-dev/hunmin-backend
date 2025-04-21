package com.api.auth.`in`.request

import com.application.auth.port.`in`.command.SignUpCommand
import io.swagger.v3.oas.annotations.media.Schema

data class SignUpRequest(
    @Schema(
        description = "회원가입 id",
        example = "root",
    )
    val username: String,
    @Schema(
        description = "회원가입 패스워드",
        example = "root",
    )
    val password: String,
) {
    fun toCommand() =
        SignUpCommand(
            username = username,
            password = password,
        )
}
