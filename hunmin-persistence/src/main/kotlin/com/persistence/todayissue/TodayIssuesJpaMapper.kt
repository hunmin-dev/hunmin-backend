package com.persistence.todayissue

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.TodayIssues
import org.springframework.stereotype.Component

@Component
class TodayIssuesJpaMapper {

    fun toEntity(aggregate: TodayIssues): TodayIssuesJpaEntity =
        TodayIssuesJpaEntity(
            id = aggregate.id,
            createdDate = aggregate.createdDate,
        )

    fun toDomain(
        group: TodayIssuesJpaEntity,
        eachDomains: List<TodayIssue>
    ) = TodayIssues(
        id = group.id,
        todayIssues = eachDomains,
        createdDate = group.createdDate,
    )
}
