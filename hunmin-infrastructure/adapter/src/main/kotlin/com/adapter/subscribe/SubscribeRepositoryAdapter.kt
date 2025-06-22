package com.adapter.subscribe

import com.domain.subscribe.Subscribe
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import com.persistence.subscribe.SubscribeJpaRepository
import com.persistence.subscribe.SubscribePersistenceMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class SubscribeRepositoryAdapter(
    private val subscribeJpaRepository: SubscribeJpaRepository,
    private val subscribePersistenceMapper: SubscribePersistenceMapper
) : SubscribeRepositoryPort {

    override fun save(subscribe: Subscribe): Subscribe {
        val entity = subscribePersistenceMapper.toEntity(subscribe)
        return subscribeJpaRepository.save(entity)
            .let { subscribePersistenceMapper.toDomain(it) }
    }

    override fun findByMemberId(memberId: Long): Subscribe? =
        subscribeJpaRepository.findByMemberId(memberId)
            ?.let { subscribePersistenceMapper.toDomain(it) }

    override fun findByIdAndMemberId(id: Long, memberId: Long): Subscribe? =
        subscribeJpaRepository.findByIdAndMemberId(id, memberId)
            ?.let { subscribePersistenceMapper.toDomain(it) }

    override fun existsByMemberId(memberId: Long): Boolean =
        subscribeJpaRepository.existsByMemberId(memberId)
}
