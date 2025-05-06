package com.api.category.`in`.request

import com.domain.category.port.`in`.command.UpdateCommand
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateRequest(
    @Schema(
        description = "카테고리 제목",
        example = "CS",
    )
    val title: String?,

    @Schema(
        description = "카테고리 수정 여부",
        defaultValue = "true",
        example = "true",
    )
    val isVisible: Boolean?,
) {
    fun toCommand() =
        UpdateCommand(
            title = title,
            isVisible = isVisible,
        )
}
