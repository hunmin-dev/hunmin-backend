package com.domain.article.port.`in`

import com.domain.article.Article
import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.dto.ArticlesSimpleResponse
import com.domain.article.port.`in`.command.CreateCommand
import com.domain.article.port.`in`.command.ReportCommand
import com.domain.article.port.`in`.command.UpdateCommand
import com.domain.article.port.`in`.query.ListArticleFindQuery

interface ArticleUseCase {
    fun create(writerId: Long, command: CreateCommand): Article
    fun delete(memberId: Long, articleId: Long): Unit
    fun update(memberId: Long, articleId: Long, command: UpdateCommand): Article
    fun report(memberId: Long, articleId: Long, command: ReportCommand): Unit
    fun findVisibleArticle(memberId: Long, articleId: Long): ArticleSimpleResponse
    fun findArticlesWithNoOffsetPaging(memberId: Long, query: ListArticleFindQuery): ArticlesSimpleResponse
}
