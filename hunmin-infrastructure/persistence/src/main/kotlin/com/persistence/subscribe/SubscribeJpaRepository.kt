package com.persistence.subscribe

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscribeJpaRepository : JpaRepository<SubscribeEntity, Long> {
    fun findByMemberId(memberId: Long): SubscribeEntity?
    fun findByIdAndMemberId(id: Long, memberId: Long): SubscribeEntity?
    fun existsByMemberId(memberId: Long): Boolean
}
