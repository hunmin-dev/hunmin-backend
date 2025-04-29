package com.application.todayissue

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.TodayIssues
import com.domain.todayissue.port.`in`.TodayIssueUseCase
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import com.domain.todayissue.port.`in`.command.TodayIssueCreateManuallyCommand
import com.domain.todayissue.port.out.TodayIssueRepositoryPort
import org.springframework.stereotype.Service

@Service
class TodayIssueService(
    private val todayIssueRepositoryPort: TodayIssueRepositoryPort
) : TodayIssueUseCase {

    override fun createTodayIssueManually(command: TodayIssueCreateManuallyCommand): TodayIssue {
        TODO()
    }

    // TODO : 구체화 때 도메인 생성 이벤트 추가 및 Listner 추가 필요 ()
    override fun createTodayIssueBatch(commands: List<TodayIssueCreateCommand>): TodayIssues {
        val todayIssues = todayIssueRepositoryPort.saveAll(commands.map { TodayIssue.from(it) })
            .let { TodayIssues.from(it) }
        return todayIssues
    }

    override fun findAllTodayIssues(): List<TodayIssue> {
        TODO("Not yet implemented")
    }
}
