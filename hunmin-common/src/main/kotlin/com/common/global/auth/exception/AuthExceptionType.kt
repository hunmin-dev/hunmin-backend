package com.common.global.auth.exception

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

    ROLE_NOT_FOUND {
      override val subject: String = "ROLE_EXCEPTION"
      override val errorCode: String = this.name
      override val message: String = "인가 정보를 찾을 수 없습니다."
      override val httpStatusCode: HttpStatusCode = HttpStatus.FORBIDDEN
    },

    INSUFFICIENT_ROLE {
      override val subject: String = "ROLE_EXCEPTION"
      override val errorCode: String = this.name
      override val message: String = "권한이 부족합니다."
      override val httpStatusCode: HttpStatusCode = HttpStatus.FORBIDDEN
    },
}
