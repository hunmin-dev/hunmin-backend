package com.bootstrap.article.`in`.request

import com.domain.article.port.`in`.command.ReportCommand
import io.swagger.v3.oas.annotations.media.Schema

data class ReportArticleRequest(

    @Schema(
        description = "신고 사유",
        required = true
    )
    val reason: String,
) {
    fun toCommand() =
        ReportCommand(
            reason = reason,
        )
}
