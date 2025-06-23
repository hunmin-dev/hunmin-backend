package com.bootstrap.subscribe.`in`.request

import com.domain.subscribe.port.`in`.command.UpdateSubscribeCommand

data class UpdateSubscribeRequest(
    val receiveArticleNotifications: Boolean? = null,
    val receiveTodayIssueNotifications: Boolean? = null,
    val receiveCommentNotifications: Boolean? = null,
    val receiveReplyNotifications: Boolean? = null,
) {
    fun toCommand(): UpdateSubscribeCommand {
        return UpdateSubscribeCommand(
            receiveArticleNotifications = receiveArticleNotifications,
            receiveTodayIssueNotifications = receiveTodayIssueNotifications,
            receiveCommentNotifications = receiveCommentNotifications,
            receiveReplyNotifications = receiveReplyNotifications
        )
    }
}
