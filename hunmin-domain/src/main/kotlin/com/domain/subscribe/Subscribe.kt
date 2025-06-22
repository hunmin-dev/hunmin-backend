package com.domain.subscribe

import com.domain.aggregate.AggregateRoot
import com.domain.subscribe.event.SubscribeEvent
import com.domain.subscribe.vo.SubscribeOptions

class Subscribe(
    override val id: Long = 0,
    val memberId: Long,
    val options: SubscribeOptions,
    val createdAt: Long = 0,
) : AggregateRoot<SubscribeEvent, Long>() {

    fun update(
        receiveArticleNotifications: Boolean?,
        receiveTodayIssueNotifications: Boolean?,
        receiveCommentNotifications: Boolean?,
        receiveReplyNotifications: Boolean?
    ) =
        Subscribe(
            id = this.id,
            memberId = this.memberId,
            options = this.options.updateOptions(
                receiveArticleNotifications = receiveArticleNotifications,
                receiveTodayIssueNotifications = receiveTodayIssueNotifications,
                receiveCommentNotifications = receiveCommentNotifications,
                receiveReplyNotifications = receiveReplyNotifications
            ),
            createdAt = this.createdAt
        ).apply {
            SubscribeEvent.updated(this)
        }

    companion object {
        fun createSubscribe(memberId: Long) =
            Subscribe(
                memberId = memberId,
                options = SubscribeOptions.createDefaultOptions()
            ).apply {
                SubscribeEvent.created(this)
            }
    }
}
