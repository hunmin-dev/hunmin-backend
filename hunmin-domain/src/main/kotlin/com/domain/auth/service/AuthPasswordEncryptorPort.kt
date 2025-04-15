package com.domain.auth.service

interface AuthPasswordEncryptorPort {

    fun encrypt(password: String): String

    fun matches(password: String, encodedPassword: String): Boolean
}
