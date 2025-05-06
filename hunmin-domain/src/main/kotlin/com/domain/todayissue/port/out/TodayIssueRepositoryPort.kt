package com.domain.todayissue.port.out

import com.domain.todayissue.TodayIssue

interface TodayIssueRepositoryPort {

    fun save(todayIssue: TodayIssue, groupId: Long): TodayIssue

    fun saveAll(todayIssues: List<TodayIssue>, groupId: Long): List<TodayIssue>

    fun findAll(): List<TodayIssue>

    fun findAllByGroupId(groupId: Long): List<TodayIssue>

    fun findByIdOrNull(id: Long): TodayIssue?
}
