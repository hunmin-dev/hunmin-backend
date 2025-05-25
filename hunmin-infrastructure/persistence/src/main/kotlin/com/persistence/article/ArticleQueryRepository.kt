package com.persistence.article

import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.dto.ArticlesSimpleResponse
import com.persistence.article.QArticleJpaEntity.articleJpaEntity
import com.persistence.auth.QAuthJpaEntity.authJpaEntity
import com.persistence.category.QCategoryJpaEntity.categoryJpaEntity
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ArticleQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findArticleById(articleId: Long): ArticleSimpleResponse? =
        jpaQueryFactory
            .select(
                Projections.constructor(
                    ArticleSimpleResponse::class.java,
                    articleJpaEntity.id,
                    articleJpaEntity.title,
                    articleJpaEntity.content,
                    articleJpaEntity.writerId,
                    authJpaEntity.username,
                    articleJpaEntity.categoryId,
                    categoryJpaEntity.title,
                    articleJpaEntity.options.isVisible,
                    articleJpaEntity.options.isReported,
                    articleJpaEntity.options.isTrending,
                    articleJpaEntity.options.isQuestion,
                    articleJpaEntity.options.isDeleted,
                    articleJpaEntity.createdAt,
                )
            )
            .from(articleJpaEntity)
            .join(authJpaEntity).on(articleJpaEntity.writerId.eq(authJpaEntity.id))
            .join(categoryJpaEntity).on(articleJpaEntity.categoryId.eq(categoryJpaEntity.id))
            .where(articleJpaEntity.id.eq(articleId))
            .fetchOne()

    fun findArticlesByQuery(
        lastArticleId: Long?,
        size: Int,
        categories: List<Long>,
        sort: String,
        direction: String,
        isQuestion: Boolean?,
        isTrending: Boolean?,
    ): ArticlesSimpleResponse {
        val sortOrder = if (direction.equals("DESC", ignoreCase = true)) Order.DESC else Order.ASC

        val orderSpecifier = when (sort) {
            "createdAt" -> com.querydsl.core.types.dsl.Expressions.stringPath("createdAt").let {
                OrderSpecifier(sortOrder, articleJpaEntity.createdAt)
            }
            "isTrending" -> OrderSpecifier(sortOrder, articleJpaEntity.options.isTrending)
            else -> OrderSpecifier(Order.DESC, articleJpaEntity.createdAt)
        }

        val results = jpaQueryFactory
            .select(
                Projections.constructor(
                    ArticleSimpleResponse::class.java,
                    articleJpaEntity.id,
                    articleJpaEntity.title,
                    articleJpaEntity.content,
                    articleJpaEntity.writerId,
                    authJpaEntity.username,
                    articleJpaEntity.categoryId,
                    categoryJpaEntity.title,
                    articleJpaEntity.options.isVisible,
                    articleJpaEntity.options.isReported,
                    articleJpaEntity.options.isTrending,
                    articleJpaEntity.options.isQuestion,
                    articleJpaEntity.options.isDeleted,
                    articleJpaEntity.createdAt
                )
            )
            .from(articleJpaEntity)
            .join(authJpaEntity).on(articleJpaEntity.writerId.eq(authJpaEntity.id))
            .join(categoryJpaEntity).on(articleJpaEntity.categoryId.eq(categoryJpaEntity.id))
            .where(
                isQuestion?.let { articleJpaEntity.options.isQuestion.eq(isQuestion)},
                isTrending?.let { articleJpaEntity.options.isTrending.eq(isTrending)},
                articleJpaEntity.categoryId.`in`(categories),
                lastArticleId?.let { articleJpaEntity.id.lt(it) }
            )
            .orderBy(orderSpecifier)
            .limit(size.toLong())
            .fetch()

        return ArticlesSimpleResponse(articles = results)
    }
}
