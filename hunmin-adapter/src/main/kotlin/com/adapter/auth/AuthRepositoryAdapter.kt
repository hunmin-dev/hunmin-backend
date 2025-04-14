package com.adapter.auth

import com.application.auth.port.out.AuthRepositoryPort
import com.domain.auth.Auth
import com.persistence.auth.AuthJpaRepository
import com.persistence.auth.AuthPersistenceMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class AuthRepositoryAdapter(
    private val authJpaRepository: AuthJpaRepository,
    private val authPersistenceMapper: AuthPersistenceMapper
) : AuthRepositoryPort {

    @Transactional
    override fun save(auth: Auth): Auth {
        val authEntity = authPersistenceMapper.toEntity(auth)
        val savedAuth = authJpaRepository.save(authEntity)
        return authPersistenceMapper.toDomain(savedAuth)
    }

    override fun findByUsername(username: String): Auth? {
        val authEntity = authJpaRepository.findByUsername(username)
        return authEntity?.let { authPersistenceMapper.toDomain(it) }
    }

    override fun existsByUsername(username: String): Boolean {
        return authJpaRepository.existsAuthByUsername(username)
    }
}
