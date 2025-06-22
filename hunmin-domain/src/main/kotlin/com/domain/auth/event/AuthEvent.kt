package com.domain.auth.event

import com.domain.aggregate.DomainEvent
import com.domain.auth.Auth
import java.time.LocalDateTime

sealed class AuthEvent : DomainEvent {

    companion object {
        fun created(auth: Auth) {
            auth.addEvent(
                AuthCreatedEvent(
                    memberId = auth.id,
                    createdDate = LocalDateTime.now(),
                )
            )
        }
    }
}

class AuthCreatedEvent(
    val memberId: Long,
    val createdDate: LocalDateTime,
) : AuthEvent()
