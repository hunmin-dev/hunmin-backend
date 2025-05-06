package com.persistence.todayissue

import com.domain.todayissue.dto.TodayIssueSimpleGroupResponse
import com.domain.todayissue.dto.TodayIssueSimpleResponse
import com.domain.todayissue.dto.TodayIssuesSimpleResponse
import com.persistence.auth.QAuthJpaEntity.authJpaEntity
import com.persistence.todayissue.QTodayIssueJpaEntity.todayIssueJpaEntity
import com.persistence.todayissue.QTodayIssuesJpaEntity.todayIssuesJpaEntity
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TodayIssuesQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory
) {

    fun findAllTodayIssuesWithNoOffsetPaging(lastIssueId: Long?, size: Int): TodayIssuesSimpleResponse {
        val query = jpaQueryFactory
            .select(
                Projections.tuple(
                    todayIssuesJpaEntity.id,
                    todayIssuesJpaEntity.createdDate,
                    todayIssueJpaEntity.id,
                    todayIssueJpaEntity.title,
                    todayIssueJpaEntity.writerId,
                    authJpaEntity.username,
                    todayIssueJpaEntity.type
                )
            )
            .from(todayIssuesJpaEntity)
            .leftJoin(todayIssueJpaEntity)
            .on(todayIssueJpaEntity.groupId.eq(todayIssuesJpaEntity.id))
            .leftJoin(authJpaEntity)
            .on(todayIssueJpaEntity.writerId.eq(authJpaEntity.id))
            .where(
                lastIssueId?.let { todayIssuesJpaEntity.id.gt(it) }
            )
            .orderBy(todayIssuesJpaEntity.id.desc())
            .limit(size.toLong())

        val groups = query.fetch()
            .groupBy { it.get(todayIssuesJpaEntity.id) }
            .map { (groupId, rows) ->
                val createdDate = rows.first().get(todayIssuesJpaEntity.createdDate)
                val todayIssues = rows
                    .filter { it.get(todayIssueJpaEntity.id) != null }
                    .map {
                        TodayIssueSimpleResponse(
                            issueId = it.get(todayIssueJpaEntity.id)!!,
                            title = it.get(todayIssueJpaEntity.title)!!,
                            writerId = it.get(todayIssueJpaEntity.writerId)!!,
                            writerName = it.get(authJpaEntity.username) ?: "Unknown",
                            type = it.get(todayIssueJpaEntity.type)!!
                        )
                    }
                TodayIssueSimpleGroupResponse(
                    groupId = groupId!!,
                    todayIssues = todayIssues,
                    createdDate = createdDate!!
                )
            }
            .distinct()

        return TodayIssuesSimpleResponse(groups = groups)
    }
}
