package com.persistence.subscribe

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscribeJpaRepository : JpaRepository<SubscribeEntity, Long> {
    fun findByUserId(userId: Long): SubscribeEntity?
    fun findByIdAndMemberId(userId: Long, memberId: Long): SubscribeEntity?
    fun existsByUserId(userId: Long): Boolean
}
