package com.persistence.subscribe

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

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

    @Column(name = "created_at", nullable = false)
    val createdAt: Long = Instant.now().toEpochMilli(),
)
