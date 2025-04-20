package com.persistence.auth

import org.springframework.data.jpa.repository.JpaRepository

interface AuthJpaRepository : JpaRepository<AuthJpaEntity, Long> {
    fun existsAuthByUsername(username: String): Boolean

    fun findByUsername(username: String): AuthJpaEntity?
}
