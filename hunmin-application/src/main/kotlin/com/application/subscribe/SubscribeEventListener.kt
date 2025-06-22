package com.application.subscribe

import com.domain.auth.event.AuthCreatedEvent
import com.domain.subscribe.port.`in`.SubscribeUseCase
import com.domain.subscribe.port.`in`.command.CreateSubscribeCommand
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SubscribeEventListener(
    private val subscribeUseCase: SubscribeUseCase
) {

    @EventListener(AuthCreatedEvent::class)
    fun saveDefaultSubscribe(event: AuthCreatedEvent) {
        subscribeUseCase.create(
            userId = event.authId,
            command = CreateSubscribeCommand()
        )
    }
}
