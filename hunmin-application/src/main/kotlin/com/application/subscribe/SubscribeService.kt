package com.application.subscribe

import com.common.global.exceptions.base.CustomException
import com.domain.subscribe.Subscribe
import com.domain.subscribe.exception.SubscribeExceptionType
import com.domain.subscribe.port.`in`.SubscribeUseCase
import com.domain.subscribe.port.`in`.command.CreateSubscribeCommand
import com.domain.subscribe.port.`in`.command.UpdateSubscribeCommand
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SubscribeService(
    private val subscribeRepositoryPort: SubscribeRepositoryPort,
) : SubscribeUseCase {

    override fun create(memberId: Long, command: CreateSubscribeCommand): Subscribe {
        log.info { "memberId: $memberId 유저, 알림 설정 생성 완료" }
        return subscribeRepositoryPort.save(Subscribe.createSubscribe(memberId))
    }

    override fun update(memberId: Long, subscribeId: Long, command: UpdateSubscribeCommand): Subscribe =
        subscribeRepositoryPort.findByIdAndMemberId(id = subscribeId, memberId = memberId)
            ?.update(
                receiveArticleNotifications = command.receiveArticleNotifications,
                receiveTodayIssueNotifications = command.receiveTodayIssueNotifications,
                receiveCommentNotifications = command.receiveCommentNotifications,
                receiveReplyNotifications = command.receiveReplyNotifications
            )
            ?.let { subscribeRepositoryPort.save(it) }
            ?: throw CustomException(SubscribeExceptionType.SUBSCRIBE_NOT_FOUND)

    override fun findByMemberId(memberId: Long): Subscribe =
        subscribeRepositoryPort.findByMemberId(memberId)
            ?: throw CustomException(SubscribeExceptionType.SUBSCRIBE_NOT_FOUND)

    companion object {
        private val log = KotlinLogging.logger { }
    }
}
