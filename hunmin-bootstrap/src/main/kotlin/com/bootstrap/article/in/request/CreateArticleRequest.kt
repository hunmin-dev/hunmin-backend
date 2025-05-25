package com.bootstrap.article.`in`.request

import com.domain.article.port.`in`.command.CreateCommand
import io.swagger.v3.oas.annotations.media.Schema

data class CreateArticleRequest(

    @Schema(
        description = "카테고리 id",
        example = "1"
    )
    val categoryId: Long,

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
        description = "노출 여부",
        defaultValue = "true",
        example = "true"
    )
    val isVisible: Boolean,

    @Schema(
        description = "질문 여부",
        defaultValue = "false",
        example = "false"
    )
    val isQuestion: Boolean,
) {
    fun toCommand() =
        CreateCommand(
            categoryId = categoryId,
            title = title,
            content = content,
            isVisible = isVisible,
            isQuestion = isQuestion
        )
}
