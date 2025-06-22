package com.persistence.subscribe

import com.domain.subscribe.Subscribe
import com.domain.subscribe.vo.SubscribeOptions
import org.springframework.stereotype.Component

@Component
class SubscribePersistenceMapper {

    fun toEntity(domain: Subscribe): SubscribeEntity =
        SubscribeEntity(
            id = domain.id,
            memberId = domain.memberId,
            receiveArticleNotifications = domain.options.receiveArticleNotifications,
            receiveTodayIssueNotifications = domain.options.receiveTodayIssueNotifications,
            receiveCommentNotifications = domain.options.receiveCommentNotifications,
            receiveReplyNotifications = domain.options.receiveReplyNotifications,
            createdAt = domain.createdAt,
        )

    fun toDomain(entity: SubscribeEntity) =
        Subscribe(
            id = entity.id,
            memberId = entity.memberId,
            SubscribeOptions(
                receiveArticleNotifications = entity.receiveArticleNotifications,
                receiveTodayIssueNotifications = entity.receiveTodayIssueNotifications,
                receiveCommentNotifications = entity.receiveCommentNotifications,
                receiveReplyNotifications = entity.receiveReplyNotifications,
            ),
            createdAt = entity.createdAt,
        )
}
