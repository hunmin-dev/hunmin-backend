package com.domain.article.event

import com.domain.aggregate.DomainEvent
import com.domain.article.Article
import java.time.Instant

sealed class ArticleEvent : DomainEvent {

    companion object {
        fun created(article: Article) {
            article.addEvent(
                ArticleCreatedEvent(
                    articleId = article.id,
                    createdDateTime = Instant.now().toEpochMilli()
                )
            )
        }

        fun updated(article: Article) {
            article.addEvent(
                ArticleUpdatedEvent(
                    articleId = article.id,
                    updatedDateTime = Instant.now().toEpochMilli(),
                )
            )
        }

        fun deleted(article: Article) {
            article.addEvent(
                ArticleDeletedEvent(
                    articleId = article.id,
                    deletedDateTime = Instant.now().toEpochMilli(),
                )
            )
        }
    }
}

class ArticleCreatedEvent(
    val articleId: Long,
    val createdDateTime: Long,
): ArticleEvent()

class ArticleUpdatedEvent(
    val articleId: Long,
    val updatedDateTime: Long,
): ArticleEvent()

class ArticleDeletedEvent(
    val articleId: Long,
    val deletedDateTime: Long,
): ArticleEvent()
