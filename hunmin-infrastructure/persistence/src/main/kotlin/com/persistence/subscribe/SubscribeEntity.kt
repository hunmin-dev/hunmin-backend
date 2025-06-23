package com.persistence.subscribe

import com.persistence.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "subscribes")
class SubscribeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "receive_article_notifications", nullable = false)
    val receiveArticleNotifications: Boolean,

    @Column(name = "receive_today_issue_notifications", nullable = false)
    val receiveTodayIssueNotifications: Boolean,

    @Column(name = "receive_comment_notifications", nullable = false)
    val receiveCommentNotifications: Boolean,

    @Column(name = "receive_reply_notifications", nullable = false)
    val receiveReplyNotifications: Boolean,
) : BaseEntity()
