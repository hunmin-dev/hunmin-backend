package com.common.global.auth.exception

import com.common.global.exceptions.base.CustomExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class TokenExceptionType : CustomExceptionType {
    INVALID_TOKEN_SIGNATURE {
        override val subject: String = "JWT_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "토큰의 서명이 잘못 되었습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.UNAUTHORIZED
    },

    MALFORMED_TOKEN {
        override val subject: String = "JWT_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "토큰의 형식이 올바르지 않습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.UNAUTHORIZED
    },

    EXPIRED_TOKEN {
        override val subject: String = "JWT_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "토큰이 만료되었습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.UNAUTHORIZED
    },

    UNSUPPORTED_TOKEN_TYPE {
        override val subject: String = "JWT_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "지원하지 않는 토큰의 형식입니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.UNAUTHORIZED
    },

    INVALID_TOKEN_VALUE {
        override val subject: String = "JWT_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "토큰의 값이 유효하지 않습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.UNAUTHORIZED
    },
}
