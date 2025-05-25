package com.domain.article.vo

import com.common.global.exceptions.base.CustomException
import com.domain.article.exception.ArticleExceptionType

data class ArticleOptions(
    val isVisible: Boolean = true,
    val isReported: Boolean = false,
    val isTrending: Boolean = false,
    val isQuestion: Boolean = false,
    val isDeleted: Boolean = false,
) {
    fun updateReport(reportState: Boolean) = copy(isReported = reportState)

    fun delete(): ArticleOptions {
        if (this.isDeleted) {
            throw CustomException(ArticleExceptionType.ALREADY_DELETED_ARTICLE)
        }
        return copy(isDeleted = true)
    }

    fun update(isVisible: Boolean?) =
        this.copy(isVisible = isVisible ?: this.isVisible)

    fun isDeletedState() =
        this.isDeleted

    fun isReportedState() =
        this.isReported

    companion object {
        fun createOptions(isVisible: Boolean, isQuestion: Boolean): ArticleOptions = ArticleOptions(
            isVisible = isVisible,
            isReported = false,
            isTrending = false,
            isQuestion = isQuestion,
            isDeleted = false,
        )
    }
}