package com.domain.subscribe.port.out

import com.domain.aggregate.AggregateRepository
import com.domain.subscribe.Subscribe

interface SubscribeRepositoryPort : AggregateRepository<Subscribe, Long> {
    fun findByMemberId(memberId: Long): Subscribe?
    fun findByIdAndMemberId(id: Long, memberId: Long): Subscribe?
    fun existsByMemberId(memberId: Long): Boolean
}
