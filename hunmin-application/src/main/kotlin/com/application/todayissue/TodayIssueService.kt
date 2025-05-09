package com.application.todayissue

import com.common.global.exceptions.base.CustomException
import com.domain.todayissue.TodayIssues
import com.domain.todayissue.event.TodayIssuesPublishedEvent
import com.domain.todayissue.exception.TodayIssueExceptionType
import com.domain.todayissue.port.`in`.TodayIssueUseCase
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import com.domain.todayissue.port.out.TodayIssuesRepositoryPort
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class TodayIssueService(
    private val todayIssuesRepositoryPort: TodayIssuesRepositoryPort
) : TodayIssueUseCase {

    // TODO 제거 예정
    // 1. repositoryPort.save(TodayIssues)후 발생하는 도메인 이벤트 핸들러. 추후 알림 정책 수립 후 정책에 맞는 애그리거트로 이전 및 구현 필요
    // 2. EventListener Adapter로 이전
    @EventListener(TodayIssuesPublishedEvent::class)
    fun sendEvent(event: TodayIssuesPublishedEvent) {
        log.debug("TodayIssues 생성 완료 및 이벤트 발송 완료")
    }

    override fun createTodayIssuesWithBatch(commands: List<TodayIssueCreateCommand>) =
        TodayIssues.from(
            commands.map { it.toTodayIssue() }
        ).apply {
            todayIssuesRepositoryPort.save(this)
        }

    override fun findAllTodayIssuesWithNoOffsetPaging(
        lastIssueId: Long?,
        size: Int
    ) =
        todayIssuesRepositoryPort.findAllTodayIssuesWithNoOffsetPaging(lastIssueId, size)

    override fun findTodayIssuesByGroupId(groupId: Long) =
        todayIssuesRepositoryPort.findByIdOrNull(groupId)
            ?: throw CustomException(TodayIssueExceptionType.TODAY_ISSUES_NOT_FOUND_EXCEPTION)

    companion object {
        private val log = KotlinLogging.logger { }
    }
}
