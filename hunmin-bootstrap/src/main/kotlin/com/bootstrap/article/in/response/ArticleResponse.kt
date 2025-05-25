package com.bootstrap.article.`in`.response

import com.domain.article.Article
import io.swagger.v3.oas.annotations.media.Schema

class ArticleResponse(
    @Schema(
        description = "글 id",
        example = "1"
    )
    val id: Long,

    @Schema(
        description = "글 제목",
        example = "자바스크립트 비동기 프로그래밍의 이해"
    )
    val title: String,

    @Schema(
        description = "글 내용",
        example = "자바스크립트 비동기 프로그래밍은 현대 웹 개발에서 필수적인 개념입니다."
    )
    val content: String,

    @Schema(
        description = "작성자 id",
        example = "1"
    )
    val writerId: Long,

    @Schema(
        description = "카테고리 id",
        example = "1"
    )
    val categoryId: Long,

    @Schema(
        description = "노출 여부",
        defaultValue = "true",
        example = "true"
    )
    val isVisible: Boolean,

    @Schema(
        description = "신고 여부",
        defaultValue = "false",
        example = "false"
    )
    val isReported: Boolean,

    @Schema(
        description = "트렌딩 여부",
        defaultValue = "false",
        example = "false"
    )
    val isTrending: Boolean,

    @Schema(
        description = "질문 여부",
        defaultValue = "false",
        example = "false"
    )
    val isQuestion: Boolean,

    @Schema(
        description = "삭제 여부",
        defaultValue = "false",
        example = "false"
    )
    val isDeleted: Boolean,

    @Schema(
        description = "글 생성 시각 (UTC)",
        example = "1000"
    )
    val createdAt: Long,
) {

    companion object {
        fun from(article: Article) =
            ArticleResponse(
                id = article.id,
                title = article.title, content = article.content,
                writerId = article.writerId, categoryId = article.categoryId,
                isVisible = article.options.isVisible, isDeleted = article.options.isDeleted, isReported = article.options.isReported,
                isTrending = article.options.isTrending, isQuestion = article.options.isQuestion,
                createdAt = article.createdAt,
            )
    }
}
