package com.domain.article.exception

import com.common.global.exceptions.base.CustomExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class ArticleExceptionType : CustomExceptionType {
    ARTICLE_NOT_FOUND {
        override val subject: String = "ARTICLE_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "글을 찾을 수 없습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.NOT_FOUND
    },

    MISMATCHED_ARTICLE_WRITER {
        override val subject: String = "ARTICLE_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "글의 작성자가 아닙니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.FORBIDDEN
    },

    ALREADY_DELETED_ARTICLE {
        override val subject: String = "ARTICLE_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "이미 삭제된 글입니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.BAD_REQUEST
    }
}
