package com.persistence.todayissue

import org.springframework.data.jpa.repository.JpaRepository

interface TodayIssueJpaRepository : JpaRepository<TodayIssueJpaEntity, Long>
