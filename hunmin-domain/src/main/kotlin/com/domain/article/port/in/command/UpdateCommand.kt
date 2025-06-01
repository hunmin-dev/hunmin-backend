package com.domain.article.port.`in`.command

data class UpdateCommand(
    val categoryId: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val isVisible: Boolean? = null,
)
