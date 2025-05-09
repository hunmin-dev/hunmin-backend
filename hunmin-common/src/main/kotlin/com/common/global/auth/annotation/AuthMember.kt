package com.common.global.auth.annotation

import com.common.global.auth.role.Role

/**
 * AuthMember가 메서드 파라미터 앞에 붙으면 Token을 AuthId로 바인딩 해준다.
 */

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthMember(
    val requiredRole: Role = Role.USER
)
