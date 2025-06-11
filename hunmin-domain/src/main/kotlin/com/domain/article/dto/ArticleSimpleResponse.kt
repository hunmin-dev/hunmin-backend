package com.domain.article.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class ArticlesSimpleResponse(
    val articles: List<ArticleSimpleResponse>,
)

data class ArticleSimpleResponse(
    val articleId: Long,
    val title: String,
    val content: String,
    val writerId: Long,
    val writerName: String,
    val categoryId: Long,
    val categoryName: String,
    val isVisible: Boolean,
    val isReported: Boolean,
    val isTrending: Boolean,
    val isQuestion: Boolean,
    val isDeleted: Boolean,
    @JsonIgnore
    val createdAt: Instant,
) {
    @get:JsonProperty("createdAt")
    val createdAtMillis: Long
        get() = createdAt.toEpochMilli()
}
