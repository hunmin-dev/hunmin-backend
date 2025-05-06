package com.common.global.exceptions.base

import org.springframework.http.HttpStatusCode

/**
 * subject: 에러가 속한 분류 (ex: AUTH_EXCEPTION, CATEGORY_EXCEPTION)
 *
 * errorCode: front-end 에서 식별할 수 있는 고유 에러 코드 (ex: ALREADY_EXISTS_CATEGORY - error enum.name 이용)
 *
 * message: 한글 에러 설명
 *
 * httpStatusCode: 응답 시 반환해야 할 HTTP 상태 코드
 */
interface CustomExceptionType {
    val subject: String
    val errorCode: String
    val message: String
    val httpStatusCode: HttpStatusCode
}
