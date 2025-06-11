package com.persistence.article

import com.domain.article.Article
import com.persistence.article.vo.ArticleOptions
import org.springframework.stereotype.Component

@Component
class ArticlePersistenceMapper {
    fun toEntity(article: Article) =
            ArticleJpaEntity(
                id = article.id,
                title = article.title,
                content = article.content,
                categoryId = article.categoryId,
                writerId = article.writerId,
                options = ArticleOptions(
                    isVisible = article.options.isVisible,
                    isReported = article.options.isReported,
                    isTrending = article.options.isTrending,
                    isQuestion = article.options.isQuestion,
                    isDeleted = article.options.isDeleted,
                )
            )

    fun toDomain(entity: ArticleJpaEntity) =
        Article(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            writerId = entity.writerId,
            categoryId = entity.categoryId,
            options = com.domain.article.vo.ArticleOptions(
                isVisible = entity.options.isVisible,
                isReported = entity.options.isReported,
                isTrending = entity.options.isTrending,
                isQuestion = entity.options.isQuestion,
                isDeleted = entity.options.isDeleted,
            ),
            createdAt = entity.createdAt.toEpochMilli(),
        )
}
