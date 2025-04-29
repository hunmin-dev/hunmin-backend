package com.domain.todayissue.port.out

import com.domain.todayissue.TodayIssue

interface TodayIssueRepositoryPort {

    fun save(todayIssue: TodayIssue): TodayIssue

    fun saveAll(todayIssues: List<TodayIssue>): List<TodayIssue>

    fun findAll(): List<TodayIssue>

    fun findByIdOrNull(id: Long): TodayIssue?
}
