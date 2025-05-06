package com.domain.todayissue.dto

import com.domain.todayissue.vo.TodayIssueType
import java.time.LocalDateTime

data class TodayIssuesSimpleResponse(
    val groups: List<TodayIssueSimpleGroupResponse>,
)

data class TodayIssueSimpleGroupResponse(
    val groupId: Long,
    val todayIssues: List<TodayIssueSimpleResponse>,
    val createdDate: LocalDateTime,
)

data class TodayIssueSimpleResponse(
    val issueId: Long,
    val title: String,
    val writerId: Long,
    val writerName: String,
    val type: TodayIssueType
)
