package com.domain.article.port.out

import com.domain.aggregate.AggregateRepository
import com.domain.article.Article

interface ArticleRepositoryPort : AggregateRepository<Article, Long>
