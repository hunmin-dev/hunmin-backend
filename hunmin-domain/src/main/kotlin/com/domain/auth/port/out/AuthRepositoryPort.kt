package com.domain.auth.port.out

import com.domain.aggregate.AggregateRepository
import com.domain.auth.Auth

interface AuthRepositoryPort : AggregateRepository<Auth, Long> {

    fun findByUsername(username: String): Auth?

    fun existsByUsername(username: String): Boolean

    fun existsById(id: Long): Boolean
}
