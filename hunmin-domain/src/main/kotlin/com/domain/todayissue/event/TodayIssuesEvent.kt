package com.domain.todayissue.event

import com.domain.aggregate.DomainEvent
import com.domain.todayissue.TodayIssue
import com.domain.todayissue.TodayIssues
import java.time.LocalDateTime

sealed class TodayIssuesEvent : DomainEvent {

    companion object {
        fun created(todayIssues: TodayIssues) {
            todayIssues.addEvent(
                TodayIssuesPublishedEvent(
                    todayIssuesId = todayIssues.id,
                    todayIssues = todayIssues.todayIssues,
                    createdDate = LocalDateTime.now()
                )
            )
        }
    }
}

class TodayIssuesPublishedEvent(
    val todayIssuesId: Long,
    val todayIssues: List<TodayIssue>,
    val createdDate: LocalDateTime,
) : TodayIssuesEvent()
