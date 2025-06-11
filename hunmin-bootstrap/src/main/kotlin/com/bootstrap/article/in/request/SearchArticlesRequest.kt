package com.bootstrap.article.`in`.request

import com.domain.article.port.`in`.query.ListArticleFindQuery
import io.swagger.v3.oas.annotations.Parameter

data class SearchArticlesRequest(
    @field:Parameter(description = "마지막으로 조회한 글 ID (첫 페이지 조회 시 생략)", required = false)
    val lastArticleId: Long? = null,

    @field:Parameter(description = "페이지 크기 (기본값: 10)", example = "10", required = false)
    val size: Int = 10,

    @field:Parameter(description = "조회할 글의 카테고리 ID (콤마로 구분)", example = "1,2", required = true)
    val categories: String,

    @field:Parameter(description = "정렬 기준 (createdAt / isTrending, 기본 createdAt)", example = "createdAt", required = false)
    val sort: String = "createdAt",

    @field:Parameter(description = "정렬 방향 (ASC / DESC, 기본 DESC)", example = "DESC", required = false)
    val direction: String = "DESC",

    @field:Parameter(description = "질문 글 조회 여부", example = "false", required = false)
    val isQuestion: Boolean? = false,

    @field:Parameter(description = "트렌딩 글 조회 여부", example = "false", required = false)
    val isTrending: Boolean? = false,
) {
    fun toQuery(): ListArticleFindQuery =
        ListArticleFindQuery(
            lastArticleId = lastArticleId,
            size = size,
            categories = categories.split(",").map {it.toLong()},
            sort = sort,
            direction = direction,
            isQuestion = isQuestion,
            isTrending = isTrending,
        )
}
