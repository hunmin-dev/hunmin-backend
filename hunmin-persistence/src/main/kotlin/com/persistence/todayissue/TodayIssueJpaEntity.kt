package com.persistence.todayissue

import com.domain.todayissue.vo.TodayIssueType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob

@Entity
class TodayIssueJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val writerId: Long,

    @Column(nullable = false, length = 128)
    val title: String,

    @Lob
    @Column(nullable = false)
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: TodayIssueType,
)
