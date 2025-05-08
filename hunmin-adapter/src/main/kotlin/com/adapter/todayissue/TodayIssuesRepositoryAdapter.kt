package com.adapter.todayissue

import com.domain.todayissue.TodayIssues
import com.domain.todayissue.dto.TodayIssuesSimpleResponse
import com.domain.todayissue.port.out.TodayIssueRepositoryPort
import com.domain.todayissue.port.out.TodayIssuesRepositoryPort
import com.persistence.todayissue.TodayIssuesJpaMapper
import com.persistence.todayissue.TodayIssuesJpaRepository
import com.persistence.todayissue.TodayIssuesQueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class TodayIssuesRepositoryAdapter(
    private val todayIssueRepositoryPort: TodayIssueRepositoryPort,
    private val todayIssuesJpaRepositoryPort: TodayIssuesJpaRepository,
    private val todayIssuesQueryRepositoryPort: TodayIssuesQueryRepository,
    private val todayIssuesJpaMapper: TodayIssuesJpaMapper,
) : TodayIssuesRepositoryPort {

    override fun findAllTodayIssuesWithNoOffsetPaging(lastIssueId: Long?, size: Int): TodayIssuesSimpleResponse {
        return todayIssuesQueryRepositoryPort.findAllTodayIssuesWithNoOffsetPaging(lastIssueId, size)
    }

    override fun save(aggregate: TodayIssues) =
        todayIssuesJpaRepositoryPort.save(todayIssuesJpaMapper.toEntity(aggregate))
            .run { todayIssuesJpaMapper.toDomain(this, todayIssueRepositoryPort.saveAll(aggregate.todayIssues, id)) }

    override fun findByIdOrNull(id: Long) =
        todayIssuesJpaRepositoryPort.findByIdOrNull(id)
            ?.let { todayIssuesJpaMapper.toDomain(it, todayIssueRepositoryPort.findAllByGroupId(it.id)) }
}
