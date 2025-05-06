package com.domain.todayissue.port.out

import com.domain.aggregate.AggregateRepository
import com.domain.todayissue.TodayIssues
import com.domain.todayissue.dto.TodayIssuesSimpleResponse

interface TodayIssuesRepositoryPort : AggregateRepository<TodayIssues, Long> {

    fun findAllTodayIssuesWithNoOffsetPaging(lastIssueId: Long?, size: Int): TodayIssuesSimpleResponse
}
