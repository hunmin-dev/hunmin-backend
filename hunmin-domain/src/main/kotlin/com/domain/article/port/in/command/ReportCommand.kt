package com.domain.article.port.`in`.command

data class ReportCommand(
    val reason: String,
    val reportState: Boolean = true,
)
