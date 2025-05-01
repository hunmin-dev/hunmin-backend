package com.domain.auth.port.`in`.command

data class SignUpCommand(
    val username: String,
    val password: String,
    val role: String = "USER",
)
