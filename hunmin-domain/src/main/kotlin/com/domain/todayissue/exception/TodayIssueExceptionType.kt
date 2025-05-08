package com.domain.todayissue.exception

import com.common.global.exceptions.base.CustomExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class TodayIssueExceptionType : CustomExceptionType {
    TODAY_ISSUES_NOT_FOUND_EXCEPTION {
        override val subject: String = "TODAY_ISSUE_EXCEPTION"
        override val errorCode: String = this.name
        override val message: String = "그룹 id에 해당하는 오늘의 이슈를 찾을 수 없습니다."
        override val httpStatusCode: HttpStatusCode = HttpStatus.NOT_FOUND
    }
}
