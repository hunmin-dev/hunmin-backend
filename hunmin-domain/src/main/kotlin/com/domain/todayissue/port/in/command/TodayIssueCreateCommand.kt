package com.domain.todayissue.port.`in`.command

import com.domain.todayissue.vo.TodayIssueType

data class TodayIssueCreateCommand(
    val writerId: Long,
    val writerUsername: String,
    val title: String,
    val content: String,
    val type: TodayIssueType,
)
