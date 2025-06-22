package com.domain.subscribe.port.out

import com.domain.subscribe.Subscribe

interface SubscribeRepositoryPort {
    fun save(subscribe: Subscribe): Subscribe
    fun findByMemberId(memberId: Long): Subscribe?
    fun findByIdAndMemberId(id: Long, memberId: Long): Subscribe?
    fun existsByMemberId(memberId: Long): Boolean
}
