package com.domain.article.port.`in`.query

data class ListArticleFindQuery(
    val lastArticleId: Long?,
    val size: Int,
    val categories: List<Long>,
    val sort: String,
    val direction: String,
    val isQuestion: Boolean?,
    val isTrending: Boolean?,
)
