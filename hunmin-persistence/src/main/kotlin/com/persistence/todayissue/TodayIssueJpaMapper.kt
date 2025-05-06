package com.persistence.todayissue

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.vo.TodayIssueContent
import com.domain.todayissue.vo.TodayIssueWriter
import com.persistence.auth.AuthJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TodayIssueJpaMapper(
    private val authJpaRepository: AuthJpaRepository
) {

    fun toEntity(domain: TodayIssue, groupId: Long): TodayIssueJpaEntity =
        TodayIssueJpaEntity(
            id = domain.id,
            writerId = domain.writer.id,
            groupId = groupId,
            title = domain.todayIssueContent.title,
            content = domain.todayIssueContent.content,
            type = domain.todayIssueContent.type,
        )

    fun toDomain(entity: TodayIssueJpaEntity): TodayIssue {
        val writer = authJpaRepository.findByIdOrNull(entity.writerId)
            ?.let { TodayIssueWriter(it.id, it.username) }
            ?: TodayIssueWriter(entity.writerId, "Unknown")

        return TodayIssue(
            id = entity.id,
            writer = writer,
            todayIssueContent = TodayIssueContent(
                title = entity.title,
                content = entity.content,
                type = entity.type
            ),
        )
    }
}
