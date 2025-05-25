package com.persistence.article.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class ArticleOptions(
    @Column(nullable = false)
    val isVisible: Boolean = true,

    @Column(nullable = false)
    val isReported: Boolean = false,

    @Column(nullable = false)
    val isTrending: Boolean = false,

    @Column(nullable = false)
    val isQuestion: Boolean = false,

    @Column(nullable = false)
    val isDeleted: Boolean = false,
)
