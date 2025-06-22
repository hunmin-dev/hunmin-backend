package com.domain.subscribe.port.`in`.command

data class CreateSubscribeCommand(
    val receiveArticleNotifications: Boolean = true,
    val receiveTodayIssueNotifications: Boolean = true,
    val receiveCommentNotifications: Boolean = true,
    val receiveReplyNotifications: Boolean = true,
)
