package com.adapter.article

import com.common.global.exceptions.base.CustomException
import com.domain.article.Article
import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.exception.ArticleExceptionType
import com.domain.article.port.out.ArticleRepositoryPort
import com.persistence.article.ArticleJpaRepository
import com.persistence.article.ArticlePersistenceMapper
import com.persistence.article.ArticleQueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class ArticleRepositoryAdapter(
    private val articleJpaRepositoryPort: ArticleJpaRepository,
    private val articleQueryRepositoryPort: ArticleQueryRepository,
    private val articlePersistenceMapper: ArticlePersistenceMapper,
): ArticleRepositoryPort {

    @Transactional
    override fun save(aggregate: Article): Article {
        val articleEntity = articlePersistenceMapper.toEntity(aggregate)
        val savedArticle = articleJpaRepositoryPort.save(articleEntity)
        return articlePersistenceMapper.toDomain(savedArticle)
    }

    override fun findByIdOrNull(id: Long): Article? =
        articleJpaRepositoryPort.findByIdOrNull(id)
            ?.let { articlePersistenceMapper.toDomain(it) }

    override fun findArticleById(articleId: Long): ArticleSimpleResponse =
        articleQueryRepositoryPort.findArticleById(articleId = articleId)
            ?: throw CustomException(ArticleExceptionType.ARTICLE_NOT_FOUND)

    override fun findArticleByQuery(
        lastArticleId: Long?,
        size: Int,
        categories: List<Long>,
        sort: String,
        direction: String,
        isQuestion: Boolean?,
        isTrending: Boolean?,
    ) =
        articleQueryRepositoryPort.findArticlesByQuery(
            lastArticleId = lastArticleId, size = size,
            categories = categories, sort = sort, direction = direction, isQuestion = isQuestion, isTrending = isTrending,
        )
}
