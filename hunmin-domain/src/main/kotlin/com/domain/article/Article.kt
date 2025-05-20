package com.domain.article

import com.domain.aggregate.AggregateRoot
import com.domain.article.event.ArticleEvent

class Article(
    override val id: Long = 0,
    val title: String,
    val content: String,
    val categoryId: Long,
    val writerId: Long,
    val isVisible: Boolean = true,
    val isBlamed: Boolean = false,
    val isQuestion: Boolean = false,
    val createdAt: Long = 0,
) : AggregateRoot<ArticleEvent, Long>() {

    companion object {
        fun createArticle(
            title: String,
            content: String,
            categoryId: Long,
            writerId: Long,
        ) = Article(title = title, content = content, categoryId = categoryId, writerId = writerId)

        fun createQuestionArticle(
            title: String,
            content: String,
            categoryId: Long,
            writerId: Long,
        ) = Article(title = title, content = content, categoryId = categoryId, writerId = writerId, isQuestion = true)
    }
}
