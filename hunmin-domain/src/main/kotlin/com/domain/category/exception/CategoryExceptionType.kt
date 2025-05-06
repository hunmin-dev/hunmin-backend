package com.domain.category.exception

import com.common.global.exceptions.base.CustomExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class CategoryExceptionType : CustomExceptionType {
    ALREADY_EXISTS_CATEGORY {
        override val subject: String = "CATEGORY_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "이미 존재하는 카테고리 제목입니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.CONFLICT
    },

    CATEGORY_NOT_FOUND {
        override val subject: String = "CATEGORY_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "카테고리를 찾을 수 없습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.NOT_FOUND
    }
}
