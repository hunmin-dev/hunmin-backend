package com.persistence.article

import com.persistence.article.vo.ArticleOptions
import com.persistence.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "article")
class ArticleJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 128)
    val title: String,

    @Lob
    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val categoryId: Long,

    @Column(nullable = false)
    val writerId: Long,

    @Embedded
    val options: ArticleOptions
) : BaseEntity()
