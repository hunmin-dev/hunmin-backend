package com.bootstrap.subscribe.`in`.request

import com.domain.subscribe.port.`in`.command.CreateSubscribeCommand

data class CreateSubscribeRequest(
    val receiveArticleNotifications: Boolean = false,
    val receiveTodayIssueNotifications: Boolean = false,
    val receiveCommentNotifications: Boolean = false,
    val receiveReplyNotifications: Boolean = false,
) {
    fun toCommand(): CreateSubscribeCommand {
        return CreateSubscribeCommand(
            receiveArticleNotifications = receiveArticleNotifications,
            receiveTodayIssueNotifications = receiveTodayIssueNotifications,
            receiveCommentNotifications = receiveCommentNotifications,
            receiveReplyNotifications = receiveReplyNotifications
        )
    }
}
