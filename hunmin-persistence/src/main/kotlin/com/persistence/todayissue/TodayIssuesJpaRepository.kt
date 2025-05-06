package com.persistence.todayissue

import org.springframework.data.jpa.repository.JpaRepository

interface TodayIssuesJpaRepository : JpaRepository<TodayIssuesJpaEntity, Long>
