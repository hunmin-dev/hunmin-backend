package com.api.todayissue.`in`.response

import com.domain.todayissue.TodayIssue
import io.swagger.v3.oas.annotations.media.Schema

data class TodayIssuesResponse(
    @Schema(description = "오늘의 이슈 모음")
    val issues: List<TodayIssueResponse>
) {

    companion object {
        fun from(todayIssues: List<TodayIssue>) = TodayIssuesResponse(
            issues = todayIssues.map { TodayIssueResponse.from(it) }
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
