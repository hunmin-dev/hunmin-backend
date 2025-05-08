package com.domain.todayissue.port.`in`.command

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.vo.TodayIssueContent
import com.domain.todayissue.vo.TodayIssueType
import com.domain.todayissue.vo.TodayIssueWriter

data class TodayIssueCreateCommand(
    val writerId: Long,
    val writerUsername: String,
    val title: String,
    val content: String,
    val type: TodayIssueType,
) {

    fun toTodayIssue(): TodayIssue {
        return TodayIssue(
            writer = TodayIssueWriter(writerId, writerUsername),
            todayIssueContent = TodayIssueContent(title, content, type)
        )
    }
}
