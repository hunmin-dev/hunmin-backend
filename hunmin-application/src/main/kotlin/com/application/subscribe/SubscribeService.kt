package com.application.subscribe

import com.common.global.exceptions.base.CustomException
import com.domain.subscribe.Subscribe
import com.domain.subscribe.event.SubscribeCreatedEvent
import com.domain.subscribe.event.SubscribeEvent
import com.domain.subscribe.event.SubscribeUpdatedEvent
import com.domain.subscribe.exception.SubscribeExceptionType
import com.domain.subscribe.port.`in`.SubscribeUseCase
import com.domain.subscribe.port.`in`.command.CreateSubscribeCommand
import com.domain.subscribe.port.`in`.command.UpdateSubscribeCommand
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class SubscribeService(
    private val subscribeRepositoryPort: SubscribeRepositoryPort,
) : SubscribeUseCase {

    // TODO : 추후 작업 시 이벤트 생성
    @EventListener(SubscribeCreatedEvent::class, SubscribeUpdatedEvent::class)
    fun sendEvent(event: SubscribeEvent) {
        when (event) {
            is SubscribeCreatedEvent -> {
                log.debug { "Subscribe 생성 완료 및 이벤트 발송 완료" }
            }

            is SubscribeUpdatedEvent -> {
                log.debug { "Subscribe 수정 완료 및 이벤트 발송 완료" }
            }
        }
    }

    // 최초 멤버 생성시 생성
    override fun create(userId: Long, command: CreateSubscribeCommand): Subscribe =
        subscribeRepositoryPort.save(Subscribe.createSubscribe(userId))

    override fun update(userId: Long, subscribeId: Long, command: UpdateSubscribeCommand): Subscribe =
        subscribeRepositoryPort.findByIdAndMemberId(subscribeId, userId)
            ?.update(
                receiveArticleNotifications = command.receiveArticleNotifications,
                receiveTodayIssueNotifications = command.receiveTodayIssueNotifications,
                receiveCommentNotifications = command.receiveCommentNotifications,
                receiveReplyNotifications = command.receiveReplyNotifications
            )
            ?.let { subscribeRepositoryPort.save(it) }
            ?: throw CustomException(SubscribeExceptionType.SUBSCRIBE_NOT_FOUND)

    override fun findByUserId(userId: Long): Subscribe =
        subscribeRepositoryPort.findByUserId(userId)
            ?: throw CustomException(SubscribeExceptionType.SUBSCRIBE_NOT_FOUND)

    companion object {
        private val log = KotlinLogging.logger { }
    }
}
