package com.api.todayissue.`in`.response

import com.domain.todayissue.TodayIssue
import com.domain.todayissue.TodayIssues
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class TodayIssuesResponse(
    @Schema(description = "오늘의 이슈 그룹 id")
    val groupId: Long,

    @Schema(description = "해당하는 오늘의 이슈 날짜")
    val date: LocalDateTime,

    @Schema(description = "오늘의 이슈 모음")
    val issues: List<TodayIssueResponse>
) {

    companion object {
        fun from(todayIssues: TodayIssues) = TodayIssuesResponse(
            groupId = todayIssues.id,
            date = todayIssues.createdDate,
            issues = todayIssues.todayIssues.map { TodayIssueResponse.from(it) }
        )
    }
}

data class TodayIssueResponse(
    @Schema(description = "오늘의 이슈 id", example = "1")
    val id: Long,

    @Schema(description = "오늘의 이슈 제목", example = "빈 등록 방법은 어떤 걸 선호 하시나요?")
    val title: String,

    @Schema(description = "오늘의 이슈 본문", example = "생성자 주입과 메서드 주입, Autowired 당신의 선택은?")
    val content: String
) {

    companion object {
        fun from(todayIssues: TodayIssue) = TodayIssueResponse(
            id = todayIssues.id,
            title = todayIssues.todayIssueContent.title,
            content = todayIssues.todayIssueContent.content
        )
    }
}
