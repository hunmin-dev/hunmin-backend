package com.domain.todayissue

class TodayIssues private constructor(
    val todayIssues: List<TodayIssue>
) {

    companion object {
        fun from(todayIssues: List<TodayIssue>): TodayIssues {
            // TODO : event 생성 -> 현재는 알림에 이용 / 도메인 이벤트 처리
            return TodayIssues(todayIssues = todayIssues)
        }
    }
}
