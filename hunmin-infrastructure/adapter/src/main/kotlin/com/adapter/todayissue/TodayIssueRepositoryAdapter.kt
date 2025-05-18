package com.adapter.todayissue

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.port.out.TodayIssueRepositoryPort
import com.persistence.todayissue.TodayIssueJpaMapper
import com.persistence.todayissue.TodayIssueJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class TodayIssueRepositoryAdapter(
    private val todayIssueJpaRepository: TodayIssueJpaRepository,
    private val todayIssueJpaMapper: TodayIssueJpaMapper
) : TodayIssueRepositoryPort {

    @Transactional
    override fun save(todayIssue: TodayIssue, groupId: Long): TodayIssue {
        val entity = todayIssueJpaMapper.toEntity(todayIssue, groupId)
        return todayIssueJpaRepository.save(entity)
            .let { todayIssueJpaMapper.toDomain(it) }
    }

    @Transactional
    override fun saveAll(todayIssues: List<TodayIssue>, groupId: Long): List<TodayIssue> {
        val entities = todayIssues.map { todayIssueJpaMapper.toEntity(it, groupId) }
        return todayIssueJpaRepository.saveAll(entities)
            .map { todayIssueJpaMapper.toDomain(it) }
    }

    override fun findAll(): List<TodayIssue> =
        todayIssueJpaRepository.findAll()
            .map { todayIssueJpaMapper.toDomain(it) }

    override fun findAllByGroupId(groupId: Long) =
        todayIssueJpaRepository.findAllByGroupId(groupId)
            .map { todayIssueJpaMapper.toDomain(it) }


    override fun findByIdOrNull(id: Long): TodayIssue? =
        todayIssueJpaRepository.findByIdOrNull(id)
            ?.let { todayIssueJpaMapper.toDomain(it) }
}
