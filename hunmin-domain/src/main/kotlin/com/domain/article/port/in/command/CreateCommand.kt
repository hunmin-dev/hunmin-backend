package com.domain.article.port.`in`.command

data class CreateCommand(
    val categoryId: Long,
    val title: String,
    val content: String,
    val isVisible: Boolean = true,
    val isQuestion: Boolean = false,
)
