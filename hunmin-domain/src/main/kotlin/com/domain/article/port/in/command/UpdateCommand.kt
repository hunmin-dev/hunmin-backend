package com.domain.article.port.`in`.command

data class UpdateCommand(
    val categoryId: Long?,
    val title: String?,
    val content: String?,
    val isVisible: Boolean?,
)
