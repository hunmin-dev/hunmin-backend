package com.api.todayissue.`in`

import com.api.todayissue.`in`.response.TodayIssuesResponse
import com.domain.todayissue.TodayIssues
import com.domain.todayissue.port.`in`.TodayIssueUseCase
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/today-issues")
@RestController
class TodayIssueController(
    private val todayIssueUseCase: TodayIssueUseCase
) : TodayIssueApi {

    // TODO : 추후 배포 시점에서 제거 예정 (테스트용)
    @PostMapping
    override fun createTodayIssuesGroupManually(commands: List<TodayIssueCreateCommand>): ResponseEntity<TodayIssues> {
        val response = todayIssueUseCase.createTodayIssuesWithBatch(commands)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    override fun findAllTodayIssuesWithNoOffsetPaging(
        @RequestParam(required = false) lastIssueId: Long?,
        @RequestParam(defaultValue = "10") size: Int
    ) = ResponseEntity.ok(
        todayIssueUseCase.findAllTodayIssuesWithNoOffsetPaging(lastIssueId, size)
    )

    @GetMapping("/{groupId}")
    override fun findTodayIssuesByGroupId(@PathVariable groupId: Long) =
        ResponseEntity.ok(
            todayIssueUseCase.findTodayIssuesByGroupId(groupId)
                .let { TodayIssuesResponse.from(it) }
        )
}
