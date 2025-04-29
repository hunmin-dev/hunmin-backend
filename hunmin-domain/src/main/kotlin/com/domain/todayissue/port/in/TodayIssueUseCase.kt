package com.domain.todayissue.port.`in`

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.TodayIssues
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import com.domain.todayissue.port.`in`.command.TodayIssueCreateManuallyCommand

interface TodayIssueUseCase {

    fun createTodayIssueManually(command: TodayIssueCreateManuallyCommand): TodayIssue

    fun createTodayIssueBatch(commands: List<TodayIssueCreateCommand>): TodayIssues

    fun findAllTodayIssues(): List<TodayIssue>
}
