package com.bootstrap.category.`in`.request

import com.domain.category.port.`in`.command.CreateCommand
import io.swagger.v3.oas.annotations.media.Schema

data class CreateRequest(
    @Schema(
        description = "카테고리 제목",
        example = "CS"
    )
    val title: String,

    @Schema(
        description = "카테고리 노출 여부",
        defaultValue = "true",
        example = "true"
    )
    val isVisible: Boolean
) {
    fun toCommand() =
        CreateCommand(
            title = title,
            isVisible = isVisible,
        )
}
