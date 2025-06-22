package com.domain.subscribe.port.`in`

import com.domain.subscribe.Subscribe
import com.domain.subscribe.port.`in`.command.CreateSubscribeCommand
import com.domain.subscribe.port.`in`.command.UpdateSubscribeCommand

interface SubscribeUseCase {
    fun create(memberId: Long, command: CreateSubscribeCommand): Subscribe
    fun update(memberId: Long, subscribeId: Long, command: UpdateSubscribeCommand): Subscribe
    fun findByMemberId(memberId: Long): Subscribe
}
