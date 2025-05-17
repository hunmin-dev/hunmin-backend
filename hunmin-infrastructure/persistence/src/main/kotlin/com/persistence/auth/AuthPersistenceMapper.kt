package com.persistence.auth

import com.common.global.auth.role.Role
import com.domain.auth.Auth
import org.springframework.stereotype.Component

@Component
class AuthPersistenceMapper {
    fun toEntity(auth: Auth): AuthJpaEntity =
        AuthJpaEntity(
            id = auth.id,
            username = auth.username,
            password = auth.password,
            role = auth.role.name,
        )

    fun toDomain(entity: AuthJpaEntity): Auth =
        Auth(
            id = entity.id,
            username = entity.username,
            password = entity.password,
            role = Role.findByName(entity.role)
        )
}
