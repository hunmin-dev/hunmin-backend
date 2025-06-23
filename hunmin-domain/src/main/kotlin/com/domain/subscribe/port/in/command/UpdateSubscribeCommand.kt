package com.domain.subscribe.port.`in`.command

data class UpdateSubscribeCommand(
    val receiveArticleNotifications: Boolean?,
    val receiveTodayIssueNotifications: Boolean?,
    val receiveCommentNotifications: Boolean?,
    val receiveReplyNotifications: Boolean?,
)
