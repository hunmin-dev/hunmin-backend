package com.common.global.auth.resolver

import com.common.global.auth.annotation.AuthMember
import com.common.global.auth.exception.AuthExceptionType
import com.common.global.auth.support.AuthenticationContext
import com.common.global.exceptions.base.CustomException
import org.springframework.core.MethodParameter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthMemberArgumentResolver(
    private val authenticationContext: AuthenticationContext,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(AuthMember::class.java) &&
            parameter.parameterType == Long::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        @Nullable mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        @Nullable binderFactory: WebDataBinderFactory?,
    ): Any? {
        val annotation = parameter.getParameterAnnotation(AuthMember::class.java)
            ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND)

        val role = authenticationContext.getRole()

        if (role.atLeast(annotation.requiredRole)) {
            throw CustomException(AuthExceptionType.INSUFFICIENT_ROLE)
        }

        return authenticationContext.getPrincipal()
    }
}
