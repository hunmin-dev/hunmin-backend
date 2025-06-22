package com.domain.subscribe.port.`in`

import com.domain.subscribe.Subscribe
import com.domain.subscribe.port.`in`.command.CreateSubscribeCommand
import com.domain.subscribe.port.`in`.command.UpdateSubscribeCommand

interface SubscribeUseCase {
    fun create(userId: Long, command: CreateSubscribeCommand): Subscribe
    fun update(userId: Long, subscribeId: Long, command: UpdateSubscribeCommand): Subscribe
    fun findByUserId(userId: Long): Subscribe
}
