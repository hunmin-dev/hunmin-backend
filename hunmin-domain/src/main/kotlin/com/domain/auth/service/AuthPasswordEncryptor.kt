package com.domain.auth.service

interface AuthPasswordEncryptor {

    fun encrypt(password: String): String

    fun matches(password: String, encodedPassword: String): Boolean
}
