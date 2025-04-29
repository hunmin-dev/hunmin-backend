package com.api.todayissue.`in`.request

import com.domain.todayissue.port.`in`.command.TodayIssueCreateManuallyCommand
import io.swagger.v3.oas.annotations.media.Schema

data class TodayIssueCreateRequest(
    @Schema(
        description = "이슈 제목",
        example = "스프링 Bean 주입 방법은 어떤 것을 선호하시나요?",
    )
    val title: String,

    @Schema(
        description = "이슈 본문",
        example = "스프링 Bean 주입 방법 생성자 vs 메서드 vs Autowired 어떤 걸 선호하는지 의견이 궁금합니다.",
    )
    val content: String,
) {

    fun toCommand() = TodayIssueCreateManuallyCommand(
        title = title,
        content = content,
    )
}
