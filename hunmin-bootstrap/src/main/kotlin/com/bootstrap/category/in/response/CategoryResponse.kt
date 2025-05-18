package com.bootstrap.category.`in`.response

import com.domain.category.Category
import io.swagger.v3.oas.annotations.media.Schema

class CategoryResponse(
    @Schema(
        description = "카테고리 id",
        example = "1"
    )
    val id: Long,

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
    val isVisible: Boolean,

    @Schema(
        description = "카테고리 생성 시각 (UTC)",
        example = "1000"
    )
    val createdAt: Long,
) {

    companion object {
        fun from(category: Category) =
            CategoryResponse(
                id = category.id,
                title = category.title,
                isVisible = category.isVisible,
                createdAt = category.createdAt
            )
    }
}
