package com.application.article

import com.common.global.auth.exception.AuthExceptionType
import com.common.global.auth.role.Role
import com.common.global.exceptions.base.CustomException
import com.common.util.throwWhen
import com.domain.article.Article
import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.dto.ArticlesSimpleResponse
import com.domain.article.exception.ArticleExceptionType
import com.domain.article.port.`in`.ArticleUseCase
import com.domain.article.port.`in`.command.CreateCommand
import com.domain.article.port.`in`.command.ReportCommand
import com.domain.article.port.`in`.command.UpdateCommand
import com.domain.article.port.`in`.query.ListArticleFindQuery
import com.domain.article.port.out.ArticleRepositoryPort
import com.domain.auth.Auth
import com.domain.auth.port.out.AuthRepositoryPort
import com.domain.category.exception.CategoryExceptionType
import com.domain.category.port.out.CategoryRepositoryPort
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepositoryPort: ArticleRepositoryPort,
    private val authRepositoryPort: AuthRepositoryPort,
    private val categoryRepositoryPort: CategoryRepositoryPort,
): ArticleUseCase {

    override fun create(writerId: Long, command: CreateCommand): Article {
        require(authRepositoryPort.existsById(writerId)) {
            CustomException(AuthExceptionType.AUTH_NOT_FOUND)
        }

        require(categoryRepositoryPort.existsById(command.categoryId)) {
            CustomException(CategoryExceptionType.CATEGORY_NOT_FOUND)
        }

        return articleRepositoryPort.save(
            Article.createArticle(
                title = command.title, content = command.content,
                categoryId = command.categoryId, writerId = writerId,
                isVisible = command.isVisible, isQuestion = command.isQuestion
            )
        )
    }

    override fun delete(memberId: Long, articleId: Long) {
        val article = validatedArticlePreModifyConditions(memberId = memberId, articleId = articleId, isOnlyAdminAllowed = false)

        val deletedArticle = article.delete()
        articleRepositoryPort.save(deletedArticle)
    }

    private fun validatedArticlePreModifyConditions(memberId: Long, articleId: Long, isOnlyAdminAllowed: Boolean, categoryId: Long? = null): Article {
        categoryId?.let {
            throwWhen(categoryRepositoryPort.findByIdOrNull(categoryId) == null) {
                CustomException(CategoryExceptionType.CATEGORY_NOT_FOUND)
            }
        }

        val member = authRepositoryPort.findByIdOrNull(memberId)
            ?: throw CustomException(AuthExceptionType.AUTH_NOT_FOUND)

        val article = articleRepositoryPort.findByIdOrNull(articleId)
            ?: throw CustomException(ArticleExceptionType.ARTICLE_NOT_FOUND)

        if (member.role !== Role.ADMIN && memberId != article.writerId) {
            throw CustomException(ArticleExceptionType.MISMATCHED_ARTICLE_WRITER)
        }

        if (member.role !== Role.ADMIN && isOnlyAdminAllowed) {
            throw CustomException(AuthExceptionType.INSUFFICIENT_ROLE)
        }

        if (member.role !== Role.ADMIN && article.isDeleted()) {
            throw CustomException(ArticleExceptionType.ARTICLE_NOT_FOUND)
        }

        return article
    }

    override fun update(memberId: Long, articleId: Long, command: UpdateCommand): Article {
        val article = validatedArticlePreModifyConditions(memberId = memberId, articleId = articleId, isOnlyAdminAllowed = false, categoryId = command.categoryId)

        val updatedArticle = article.update(
            categoryId = command.categoryId,
            title = command.title, content = command.content,
            isVisible = command.isVisible
        )

        return articleRepositoryPort.save(updatedArticle)
    }

    override fun report(memberId: Long, articleId: Long, command: ReportCommand) {
        val article = validatedArticlePreModifyConditions(memberId = memberId, articleId = articleId, isOnlyAdminAllowed = true)

        val reportedArticle = article.updateReport(reportState = command.reportState)
        articleRepositoryPort.save(reportedArticle)
    }

    override fun find(memberId: Long, articleId: Long): ArticleSimpleResponse {
        val articleResponse = articleRepositoryPort.findArticleById(articleId = articleId)

        val member = authRepositoryPort.findByIdOrNull(memberId)
            ?: Auth.anonymousAuth()

        throwWhen(member.role != Role.ADMIN && articleResponse.isDeleted) {
            throw CustomException(ArticleExceptionType.ARTICLE_NOT_FOUND)
        }

        throwWhen(member.role != Role.ADMIN && memberId != articleResponse.writerId && !articleResponse.isVisible) {
            throw CustomException(ArticleExceptionType.ARTICLE_NOT_FOUND)
        }

        return articleResponse
    }

    override fun findArticlesWithNoOffsetPaging(
        memberId: Long,
        query: ListArticleFindQuery
    ): ArticlesSimpleResponse {
        val articlesResponse = articleRepositoryPort.findArticleByQuery(
            lastArticleId = query.lastArticleId,
            size = query.size,
            categories = query.categories,
            sort = query.sort,
            direction = query.direction,
            isQuestion = query.isQuestion,
            isTrending = query.isTrending,
        )

        return articlesResponse
    }
}
