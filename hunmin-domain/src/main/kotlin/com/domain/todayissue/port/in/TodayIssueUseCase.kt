package com.domain.todayissue.port.`in`

import com.domain.todayissue.TodayIssues
import com.domain.todayissue.dto.TodayIssuesSimpleResponse
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand

interface TodayIssueUseCase {

    fun createTodayIssuesWithBatch(commands: List<TodayIssueCreateCommand>): TodayIssues

    fun findTodayIssuesByGroupId(groupId: Long): TodayIssues

    fun findAllTodayIssuesWithNoOffsetPaging(
        lastIssueId: Long?,
        size: Int
    ): TodayIssuesSimpleResponse
}
