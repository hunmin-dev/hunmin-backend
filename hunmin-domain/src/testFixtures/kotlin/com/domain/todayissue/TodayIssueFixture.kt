package com.domain.todayissue

import com.domain.todayissue.dto.TodayIssueSimpleGroupResponse
import com.domain.todayissue.dto.TodayIssueSimpleResponse
import com.domain.todayissue.dto.TodayIssuesSimpleResponse
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import com.domain.todayissue.vo.TodayIssueContent
import com.domain.todayissue.vo.TodayIssueType
import com.domain.todayissue.vo.TodayIssueWriter
import java.time.LocalDateTime

class TodayIssueFixture {

    companion object {
        private val DATE = LocalDateTime.of(2020, 1, 1, 1, 1, 1)

        fun 오늘의_이슈_생성_커맨드(): TodayIssueCreateCommand {
            return TodayIssueCreateCommand(
                writerId = 1L,
                writerUsername = "username",
                title = "스프링에 대해",
                content = "블라블라",
                type = TodayIssueType.BACKEND
            )
        }

        fun 오늘의_이슈_생성(): TodayIssue {
            return TodayIssue(
                id = 1L,
                writer = TodayIssueWriter(id = 1L, username = "username"),
                todayIssueContent = TodayIssueContent(
                    title = "스프링에 대해",
                    content = "블라블라",
                    type = TodayIssueType.BACKEND
                )
            )
        }

        fun 오늘의_이슈_그룹_생성(): TodayIssues {
            return TodayIssues(
                id = 1L,
                todayIssues = listOf(오늘의_이슈_생성()),
                createdDate = DATE
            )
        }

        fun 오늘의_이슈_페이징_생성(): TodayIssuesSimpleResponse {
            return TodayIssuesSimpleResponse(
                groups = listOf(
                    TodayIssueSimpleGroupResponse(
                        groupId = 1L,
                        todayIssues = listOf(
                            TodayIssueSimpleResponse(
                                issueId = 1L,
                                title = "스프링에 대해",
                                writerId = 1L,
                                writerName = "username",
                                type = TodayIssueType.BACKEND
                            )
                        ),
                        createdDate = DATE
                    )
                )
            )
        }
    }
}
