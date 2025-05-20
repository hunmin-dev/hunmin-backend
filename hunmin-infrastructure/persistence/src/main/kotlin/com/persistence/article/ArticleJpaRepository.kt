package com.persistence.article

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleJpaRepository : JpaRepository<ArticleJpaEntity, Long>
