package com.common.global.auth.token

import com.common.global.auth.role.Role

interface TokenProvider {
    fun create(id: Long, role: Role): String

    fun extractId(token: String): Long

    fun extractRole(token: String): Role
}
