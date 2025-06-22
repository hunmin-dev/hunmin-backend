package com.bootstrap.subscribe.`in`.response

import com.domain.subscribe.Subscribe

data class SubscribeResponse(
    val id: Long,
    val memberId: Long,
    val receiveArticleNotifications: Boolean,
    val receiveTodayIssueNotifications: Boolean,
    val receiveCommentNotifications: Boolean,
    val receiveReplyNotifications: Boolean,
) {
    companion object {
        fun from(subscribe: Subscribe): SubscribeResponse {
            return SubscribeResponse(
                id = subscribe.id,
                memberId = subscribe.memberId,
                receiveArticleNotifications = subscribe.options.receiveArticleNotifications,
                receiveTodayIssueNotifications = subscribe.options.receiveTodayIssueNotifications,
                receiveCommentNotifications = subscribe.options.receiveCommentNotifications,
                receiveReplyNotifications = subscribe.options.receiveReplyNotifications
            )
        }
    }
}
