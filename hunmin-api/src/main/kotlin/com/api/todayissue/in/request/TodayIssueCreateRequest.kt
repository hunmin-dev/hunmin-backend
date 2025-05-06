package com.api.todayissue.`in`.request

import com.domain.todayissue.port.`in`.command.TodayIssueCreateManuallyCommand
import com.domain.todayissue.vo.TodayIssueType
import io.swagger.v3.oas.annotations.media.Schema

data class TodayIssueCreateRequest(
    @Schema(
        description = "작성자 id",
        example = "1",
    )
    val writerId: Long,

    @Schema(
        description = "작성자 닉네임",
        example = "올라",
    )
    val writerUsername: String,

    @Schema(
        description = "이슈 id",
        example = "1",
    )
    val title: String,
    val content: String,
    val type: TodayIssueType,
) {

    fun toCommand() = TodayIssueCreateManuallyCommand(
        title = title,
        content = content,
    )
}
