package com.api.category.`in`

import com.api.category.`in`.request.CreateRequest
import com.api.category.`in`.request.UpdateRequest
import com.api.category.`in`.response.CategoryResponse
import com.common.global.exceptions.base.ExceptionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Category API")
interface CategoryApi {
    @Operation(summary = "카테고리 생성")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "카테고리 생성 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "값 누락으로 인한 생성 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
              responseCode = "401",
                description = "인증 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한 부족으로 인한 생성 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 존재하는 카테고리 제목으로 인한 생성 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            )
        ]
    )
    @PostMapping
    fun create(
        @Parameter(hidden = true)
        memberId: Long,
        @RequestBody request: CreateRequest,
    ): ResponseEntity<CategoryResponse>

    @Operation(summary = "카테고리 수정")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "카테고리 수정 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "인증 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한 부족으로 인한 수정 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "카테고리 조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 존재하는 카테고리 제목으로 인한 생성 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            )
        ]
    )
    @PatchMapping("/{categoryId}")
    fun update(
        @Parameter(hidden = true)
        memberId: Long,
        @Parameter(
            description = "수정할 카테고리의 ID",
            example = "1"
        )
        @PathVariable categoryId: Long,
        @RequestBody request: UpdateRequest,
    ): ResponseEntity<CategoryResponse>
}
