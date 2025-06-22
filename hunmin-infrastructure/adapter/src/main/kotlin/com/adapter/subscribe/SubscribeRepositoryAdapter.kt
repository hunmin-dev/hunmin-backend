package com.adapter.subscribe

import com.domain.subscribe.Subscribe
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import com.persistence.subscribe.SubscribeEntity
import com.persistence.subscribe.SubscribeJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class SubscribeRepositoryAdapter(
    private val subscribeJpaRepository: SubscribeJpaRepository,
) : SubscribeRepositoryPort {

    override fun save(subscribe: Subscribe): Subscribe {
        val entity = SubscribeEntity.from(subscribe)
        return subscribeJpaRepository.save(entity).toDomain()
    }

    override fun findByUserId(userId: Long): Subscribe? {
        return subscribeJpaRepository.findByUserId(userId)
            ?.toDomain()
    }

    override fun findByIdAndMemberId(id: Long, memberId: Long): Subscribe? {
        return subscribeJpaRepository.findByIdAndMemberId(id, memberId)
            ?.toDomain()
    }

    override fun existsByUserId(userId: Long): Boolean {
        return subscribeJpaRepository.existsByUserId(userId)
    }
}
