package com.bootstrap.global.config

import com.common.global.auth.interceptor.LoginValidCheckerInterceptor
import com.common.global.auth.interceptor.ParseMemberIdFromTokenInterceptor
import com.common.global.auth.interceptor.PathMatcherInterceptor
import com.common.global.auth.support.HttpMethod.GET
import com.common.global.auth.support.HttpMethod.OPTIONS
import com.common.global.auth.support.HttpMethod.PATCH
import com.common.global.auth.support.HttpMethod.POST
import com.common.global.auth.support.HttpMethod.DELETE
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthConfig(
    private val parseMemberIdFromTokenInterceptor: ParseMemberIdFromTokenInterceptor,
    private val loginValidCheckerInterceptor: LoginValidCheckerInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(parseMemberIdFromTokenInterceptor())
        registry.addInterceptor(loginValidCheckerInterceptor())
    }

    private fun parseMemberIdFromTokenInterceptor(): HandlerInterceptor =
        PathMatcherInterceptor(parseMemberIdFromTokenInterceptor)
            .excludePathPattern("/**", OPTIONS)
            .addPathPatterns("/category/**", POST, PATCH)
            .addPathPatterns("/articles/**", GET, POST, PATCH, DELETE)

    private fun loginValidCheckerInterceptor(): HandlerInterceptor =
        PathMatcherInterceptor(loginValidCheckerInterceptor)
            .excludePathPattern("/**", OPTIONS)
            .excludePathPattern("/auth", POST, GET)
            .addPathPatterns("/auth/test", GET)
            .addPathPatterns("/category/**", POST, PATCH)
            .addPathPatterns("/articles/**", POST, PATCH, DELETE)
            .excludePathPattern("/articles/**", GET)
}
