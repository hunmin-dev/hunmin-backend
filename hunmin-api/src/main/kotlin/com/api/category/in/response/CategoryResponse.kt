package com.api.category.`in`.response

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
    val createdAt : Long,
)
