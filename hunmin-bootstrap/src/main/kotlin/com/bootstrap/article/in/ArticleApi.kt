package com.bootstrap.article.`in`

import com.bootstrap.article.`in`.request.CreateArticleRequest
import com.bootstrap.article.`in`.request.ReportArticleRequest
import com.bootstrap.article.`in`.request.SearchArticlesRequest
import com.bootstrap.article.`in`.request.UpdateArticleRequest
import com.bootstrap.article.`in`.response.ArticleResponse
import com.common.global.exceptions.base.ExceptionResponse
import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.dto.ArticlesSimpleResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Article API")
interface ArticleApi {

    @Operation(summary = "지식 공유 글 생성")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "지식 공유 글 생성 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleResponse::class)
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
                responseCode = "404",
                description = "카테고리 또는 회원 조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
        ]
    )
    @PostMapping
    fun create(
        @Parameter(hidden = true)
        memberId: Long,
        @RequestBody request: CreateArticleRequest,
    ): ResponseEntity<ArticleResponse>

    @Operation(summary = "지식 공유 글 삭제")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "지식 공유 글 삭제 성공"
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
                description = "권한 부족 / 중복 삭제로 인한 삭제 불가",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
        ]
    )
    @DeleteMapping
    fun delete(
        @Parameter(hidden = true)
        memberId: Long,
        @Parameter(
            description = "삭제할 글의 ID",
            example = "1"
        )
        @PathVariable articleId: Long
    ): ResponseEntity<Void>

    @Operation(summary = "지식 공유 글 수정")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "지식 공유 글 수정 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleResponse::class)
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
                description = "카테고리 또는 회원 조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
        ]
    )
    @PatchMapping("/{articleId}")
    fun update(
        @Parameter(hidden = true)
        memberId: Long,
        @Parameter(
            description = "수정할 글의 ID",
            example = "1"
        )
        @PathVariable articleId: Long,
        @RequestBody request: UpdateArticleRequest,
    ): ResponseEntity<ArticleResponse>

    @Operation(summary = "지식 공유 글 신고")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "지식 공유 글 신고 성공"
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
                responseCode = "404",
                description = "조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
        ]
    )
    @PostMapping
    fun report(
        @Parameter(hidden = true)
        memberId: Long,
        @Parameter(
            description = "신고할 글의 ID",
            example = "1"
        )
        @PathVariable articleId: Long,
        @RequestBody request: ReportArticleRequest,
    ): ResponseEntity<Void>

    @Operation(summary = "지식 공유 글 단건 조회")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "지식 공유 글 단건 조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleSimpleResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "조회 실패",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionResponse::class)
                )]
            ),
        ]
    )
    @GetMapping
    fun find(
        @Parameter(hidden = true)
        memberId: Long,
        @Parameter(
            description = "조회할 글의 ID",
            example = "1"
        )
        @PathVariable articleId: Long,
    ): ResponseEntity<ArticleSimpleResponse>

    @Operation(summary = "지식 공유 글 목록 조회")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "지식 공유 글 목록 조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleSimpleResponse::class)
                )]
            ),
        ]
    )
    @GetMapping
    fun findArticlesWithNoOffsetPaging(
        @Parameter(hidden = true)
        memberId: Long,
        @ParameterObject
        @ModelAttribute request: SearchArticlesRequest,
    ): ResponseEntity<ArticlesSimpleResponse>
}
