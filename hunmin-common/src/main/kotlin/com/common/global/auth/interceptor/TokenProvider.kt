package com.common.global.auth.interceptor

interface TokenProvider {

    fun create(id: Long): String

    fun extract(token: String): Long
}
