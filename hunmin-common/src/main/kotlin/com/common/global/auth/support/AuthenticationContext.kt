package com.common.global.auth.support

import com.common.global.auth.exception.AuthExceptionType
import com.common.global.auth.role.Role
import com.common.global.exceptions.base.CustomException
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope

@RequestScope
@Component
class AuthenticationContext(
    private var memberId: Long? = null,
    private var role: Role? = null,
) {
    fun setAuthentication(memberId: Long, role: Role) {
        this.memberId = memberId
        this.role = role
    }

    fun getPrincipal(): Long =
        memberId
            ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND_EXCEPTION)

    fun getRole(): Role =
        role ?: throw CustomException(AuthExceptionType.ROLE_NOT_FOUND_EXCEPTION)

    fun setAnonymous() {
        this.memberId = ANONYMOUS_MEMBER
        this.role = null
    }

    companion object {
        private const val ANONYMOUS_MEMBER = -1L
    }
}
