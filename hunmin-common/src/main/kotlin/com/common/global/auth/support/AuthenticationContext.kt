package com.common.global.auth.support

import com.common.global.auth.exception.AuthExceptionType
import com.common.global.auth.role.Role
import com.common.global.exceptions.base.CustomException
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope

@RequestScope
@Component
class AuthenticationContext(
    private var memberId: Long? = ANONYMOUS_MEMBER,
    private var role: Role? = Role.ANONYMOUS,
) {
    fun setAuthentication(memberId: Long, role: Role) {
        this.memberId = memberId
        this.role = role
    }

    fun getPrincipal(): Long =
        memberId
            ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND)

    fun getRole(): Role =
        role ?: Role.ANONYMOUS

    fun setAnonymous() {
        this.memberId = ANONYMOUS_MEMBER
        this.role = Role.ANONYMOUS
    }

    companion object {
        private const val ANONYMOUS_MEMBER = -1L
    }
}
