package com.domain.auth.exception

import com.common.global.exceptions.base.CustomExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class AuthExceptionType : CustomExceptionType {
    AUTH_NOT_FOUND {
        override val subject: String = "AUTH_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "인증 정보를 찾을 수 없습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.NOT_FOUND
    },

    ALREADY_EXISTS_USERNAME {
        override val subject: String = "AUTH_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "이미 존재하는 username입니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.CONFLICT
    },

    INVALID_PASSWORD {
        override val subject: String = "AUTH_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "패스워드가 잘못 됐습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.UNAUTHORIZED
    },
}
