package com.domain.todayissue

import com.domain.aggregate.AggregateRoot
import com.domain.todayissue.event.TodayIssuesEvent
import java.time.LocalDateTime

data class TodayIssues(
    override val id: Long = 0L,
    val todayIssues: List<TodayIssue>,
    val createdDate: LocalDateTime,
) : AggregateRoot<TodayIssuesEvent, Long>() {

    companion object {
        fun from(todayIssues: List<TodayIssue>): TodayIssues {
            return TodayIssues(
                todayIssues = todayIssues,
                createdDate = LocalDateTime.now()
            ).apply {
                TodayIssuesEvent.created(this)
            }
        }
    }
}
