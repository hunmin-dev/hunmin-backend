package com.domain.subscribe.port.`in`.command

data class CreateSubscribeCommand(
    val receiveArticleNotifications: Boolean = DEFAULT_NOTIFICATION_RECEIVE_VALUE,
    val receiveTodayIssueNotifications: Boolean = DEFAULT_NOTIFICATION_RECEIVE_VALUE,
    val receiveCommentNotifications: Boolean = DEFAULT_NOTIFICATION_RECEIVE_VALUE,
    val receiveReplyNotifications: Boolean = DEFAULT_NOTIFICATION_RECEIVE_VALUE,
) {

    companion object {
        private const val DEFAULT_NOTIFICATION_RECEIVE_VALUE = false;
    }
}
