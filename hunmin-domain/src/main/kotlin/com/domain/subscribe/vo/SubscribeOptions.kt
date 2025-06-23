package com.domain.subscribe.vo

data class SubscribeOptions(
    val receiveArticleNotifications: Boolean = false,
    val receiveTodayIssueNotifications: Boolean = false,
    val receiveCommentNotifications: Boolean = false,
    val receiveReplyNotifications: Boolean = false,
) {

    fun updateOptions(
        receiveArticleNotifications: Boolean? = null,
        receiveTodayIssueNotifications: Boolean? = null,
        receiveCommentNotifications: Boolean? = null,
        receiveReplyNotifications: Boolean? = null
    ) = copy(
        receiveArticleNotifications = receiveArticleNotifications ?: this.receiveArticleNotifications,
        receiveTodayIssueNotifications = receiveTodayIssueNotifications ?: this.receiveTodayIssueNotifications,
        receiveCommentNotifications = receiveCommentNotifications ?: this.receiveCommentNotifications,
        receiveReplyNotifications = receiveReplyNotifications ?: this.receiveReplyNotifications
    )

    internal companion object {
        private const val DEFAULT_NOTIFICATIONS_RECEIVE = false

        fun createDefaultOptions(): SubscribeOptions = SubscribeOptions(
            receiveArticleNotifications = DEFAULT_NOTIFICATIONS_RECEIVE,
            receiveTodayIssueNotifications = DEFAULT_NOTIFICATIONS_RECEIVE,
            receiveCommentNotifications = DEFAULT_NOTIFICATIONS_RECEIVE,
            receiveReplyNotifications = DEFAULT_NOTIFICATIONS_RECEIVE
        )
    }
}
