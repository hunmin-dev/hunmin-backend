package com.domain.subscribe.port.out

import com.domain.subscribe.Subscribe

interface SubscribeRepositoryPort {
    fun save(subscribe: Subscribe): Subscribe
    fun findByUserId(userId: Long): Subscribe?
    fun findByIdAndMemberId(id: Long, memberId: Long): Subscribe?
    fun existsByUserId(userId: Long): Boolean
}
