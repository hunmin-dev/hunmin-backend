package com.domain.subscribe.exception

import com.common.global.exceptions.base.CustomExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class SubscribeExceptionType : CustomExceptionType {

    SUBSCRIBE_NOT_FOUND {
        override val subject: String = "SUBSCRIBE_NOT_FOUND"
        override val errorCode: String = this.name
        override val message: String = "알림 구독 정보를 찾을 수 없습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.NOT_FOUND
    }
}
