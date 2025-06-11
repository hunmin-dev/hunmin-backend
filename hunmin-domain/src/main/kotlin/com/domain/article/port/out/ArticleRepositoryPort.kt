package com.domain.article.port.out

import com.domain.aggregate.AggregateRepository
import com.domain.article.Article
import com.domain.article.dto.ArticleSimpleResponse
import com.domain.article.dto.ArticlesSimpleResponse

interface ArticleRepositoryPort : AggregateRepository<Article, Long> {

    fun findArticleById(articleId: Long): ArticleSimpleResponse
    fun findArticleByQuery(lastArticleId: Long?, size: Int, categories: List<Long>, sort: String, direction: String, isQuestion: Boolean?, isTrending: Boolean?): ArticlesSimpleResponse
}
