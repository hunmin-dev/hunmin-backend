package com.domain.todayissue

import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import com.domain.todayissue.vo.TodayIssueContent
import com.domain.todayissue.vo.TodayIssueWriter

class TodayIssue(
    val id: Long = 0,
    val writer: TodayIssueWriter,
    val todayIssueContent: TodayIssueContent
) {

    companion object {
        fun from(command: TodayIssueCreateCommand) = TodayIssue(
            writer = TodayIssueWriter(command.writerId, command.writerUsername),
            todayIssueContent = TodayIssueContent(command.title, command.content, command.type)
        )
    }
}
