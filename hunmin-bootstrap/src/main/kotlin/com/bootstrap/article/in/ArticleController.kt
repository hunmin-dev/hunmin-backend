package com.bootstrap.article.`in`

import com.bootstrap.article.`in`.request.CreateArticleRequest
import com.bootstrap.article.`in`.request.ReportArticleRequest
import com.bootstrap.article.`in`.request.SearchArticlesRequest
import com.bootstrap.article.`in`.request.UpdateArticleRequest
import com.bootstrap.article.`in`.response.ArticleResponse
import com.common.global.auth.annotation.AuthMember
import com.common.global.auth.role.Role
import com.domain.article.port.`in`.ArticleUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/articles")
@RestController
class ArticleController(
    private val articleUseCase: ArticleUseCase,
) : ArticleApi {

    @PostMapping
    override fun create(
        @AuthMember(requiredRole = Role.USER) memberId: Long,
        @RequestBody request: CreateArticleRequest
    ) =
        articleUseCase.create(writerId = memberId, command = request.toCommand())
            .let {
                ResponseEntity.status(HttpStatus.CREATED)
                    .body(ArticleResponse.from(it))
            }

    @DeleteMapping("/{articleId}")
    override fun delete(
        @AuthMember(requiredRole = Role.USER) memberId: Long,
        @PathVariable articleId: Long
    ) =
        articleUseCase.delete(memberId = memberId, articleId = articleId)
            .let {
                ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build<Void>()
            }

    @PatchMapping("/{articleId}")
    override fun update(
        @AuthMember(requiredRole = Role.USER) memberId: Long,
        @PathVariable articleId: Long,
        @RequestBody request: UpdateArticleRequest
    ) =
        articleUseCase.update(memberId = memberId, articleId = articleId, command = request.toCommand())
            .let {
                ResponseEntity.status(HttpStatus.OK)
                    .body(ArticleResponse.from(it))
            }

    @PostMapping("/{articleId}")
    override fun report(
        @AuthMember(requiredRole = Role.USER) memberId: Long,
        @PathVariable articleId: Long,
        @RequestBody request: ReportArticleRequest,
    ) =
        articleUseCase.report(memberId = memberId, articleId = articleId, command = request.toCommand())
            .let {
                ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build<Void>()
            }

    @GetMapping("/{articleId}")
    override fun find(
        @AuthMember memberId: Long,
        articleId: Long,
    ) =
        articleUseCase.find(memberId = memberId, articleId = articleId)
            .let {
                ResponseEntity.status(HttpStatus.OK)
                    .body(it)
            }

    @GetMapping
    override fun findArticlesWithNoOffsetPaging(
        @AuthMember memberId: Long,
        @ModelAttribute request: SearchArticlesRequest
    ) =
        articleUseCase.findArticlesWithNoOffsetPaging(memberId = memberId, query = request.toQuery())
            .let {
                ResponseEntity.status(HttpStatus.OK)
                .body(it)
            }
}
