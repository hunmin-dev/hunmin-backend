package com.domain.subscribe.event

import com.domain.aggregate.DomainEvent
import com.domain.subscribe.Subscribe
import java.time.Instant

sealed class SubscribeEvent : DomainEvent {

    companion object {
        fun created(subscribe: Subscribe) {
            subscribe.addEvent(
                SubscribeCreatedEvent(
                    subscribeId = subscribe.id,
                    createdDateTime = Instant.now().toEpochMilli()
                )
            )
        }

        fun updated(subscribe: Subscribe) {
            subscribe.addEvent(
                SubscribeUpdatedEvent(
                    subscribeId = subscribe.id,
                    updatedDateTime = Instant.now().toEpochMilli(),
                )
            )
        }
    }
}

class SubscribeCreatedEvent(
    val subscribeId: Long,
    val createdDateTime: Long,
) : SubscribeEvent()

class SubscribeUpdatedEvent(
    val subscribeId: Long,
    val updatedDateTime: Long,
) : SubscribeEvent()
