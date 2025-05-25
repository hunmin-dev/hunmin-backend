package com.common.global.auth.role

import com.common.global.auth.exception.AuthExceptionType
import com.common.global.exceptions.base.CustomException

/**
 * priority가 더 높을수록 높은 권한 의미
 */
enum class Role(
    private val priority: Int
) {

    ANONYMOUS(-1),
    USER(1),
    ADMIN(2);

    companion object {
        fun findByName(name: String): Role =
            entries.firstOrNull() { it.name.equals(name, ignoreCase = true) }
                ?: throw CustomException(AuthExceptionType.ROLE_NOT_FOUND)
    }

    fun atLeast(other: Role): Boolean {
        return this.priority < other.priority
    }
}
