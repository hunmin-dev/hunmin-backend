package com.domain.category.port.`in`.command

data class CreateCommand(
    val title: String,
    val isVisible: Boolean = true,
)
