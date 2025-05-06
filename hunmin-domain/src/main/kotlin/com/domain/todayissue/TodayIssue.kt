package com.domain.todayissue

import com.domain.todayissue.vo.TodayIssueContent
import com.domain.todayissue.vo.TodayIssueWriter

class TodayIssue(
    val id: Long = 0,
    val writer: TodayIssueWriter,
    val todayIssueContent: TodayIssueContent
)
