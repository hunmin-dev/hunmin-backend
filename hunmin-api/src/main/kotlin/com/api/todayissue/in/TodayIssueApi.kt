package com.api.todayissue.`in`

import com.api.todayissue.`in`.response.TodayIssuesResponse
import com.domain.todayissue.TodayIssues
import com.domain.todayissue.dto.TodayIssuesSimpleResponse
import com.domain.todayissue.port.`in`.command.TodayIssueCreateCommand
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/today-issues")
@Tag(name = "TodayIssue API")
interface TodayIssueApi {

    @Operation(summary = "오늘의 이슈 그룹 수동 등록")
    @PostMapping
    fun createTodayIssuesGroupManually(
        @RequestBody commands: List<TodayIssueCreateCommand>
    ): ResponseEntity<TodayIssues>

    @Operation(summary = "오늘의 이슈 No-Offset 페이징 목록 조회")
    @GetMapping
    fun findAllTodayIssuesWithNoOffsetPaging(
        @Parameter(description = "마지막으로 조회한 이슈 ID (첫 페이지 조회시는 생략)")
        @RequestParam(required = false) lastIssueId: Long?,

        @Parameter(description = "페이지 크기 (기본값: 10)")
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<TodayIssuesSimpleResponse>

    @Operation(summary = "id에 해당하는 오늘의 이슈 조회")
    @GetMapping("/{groupId}")
    fun findTodayIssuesByGroupId(
        @Parameter(description = "오늘의 이슈 group-id")
        @PathVariable groupId: Long,
    ): ResponseEntity<TodayIssuesResponse>
}
