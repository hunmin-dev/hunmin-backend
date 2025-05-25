package com.domain.article

import com.domain.aggregate.AggregateRoot
import com.domain.article.event.ArticleEvent
import com.domain.article.vo.ArticleOptions

class Article(
    override val id: Long = 0,
    val title: String,
    val content: String,
    val writerId: Long,
    val categoryId: Long,
    val options: ArticleOptions,
    val createdAt: Long = 0,
) : AggregateRoot<ArticleEvent, Long>() {

    fun delete(): Article =
        Article(
            id = id,
            title = title,
            content = content,
            writerId = writerId,
            categoryId = categoryId,
            options = options.delete(),
            createdAt = createdAt
        )

    fun update(categoryId: Long?, title: String?, content: String?, isVisible: Boolean?): Article =
        Article(
            id = id,
            title = title ?: this.title,
            content = content ?: this.content,
            writerId = this.writerId,
            categoryId = categoryId ?: this.categoryId,
            options = this.options.update(isVisible = isVisible),
            createdAt = this.createdAt,
        )

    fun isDeleted() =
        this.options.isDeletedState()

    fun updateReport(reportState: Boolean): Article {
        return Article(
            id = id,
            title = title,
            content = content,
            writerId = this.writerId,
            categoryId = categoryId,
            options = this.options.updateReport(reportState),
            createdAt = this.createdAt,
        )
    }

    companion object {
        fun createArticle(
            title: String,
            content: String,
            categoryId: Long,
            writerId: Long,
            isVisible: Boolean,
            isQuestion: Boolean,
        ) = Article(
            title = title, content = content, categoryId = categoryId, writerId = writerId,
            options = ArticleOptions.createOptions(isVisible = isVisible, isQuestion = isQuestion)
        )
    }
}
