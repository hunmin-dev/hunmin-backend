package com.api.todayissue.`in`

import com.api.todayissue.`in`.request.TodayIssueCreateRequest
import com.api.todayissue.`in`.response.TodayIssuesResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/today-issues")
@Tag(name = "TodayIssue API")
interface TodayIssueApi {

    @Operation(summary = "오늘의 이슈 셀프 등록")
    @PostMapping
    fun uploadIssue(@RequestBody request: TodayIssueCreateRequest): ResponseEntity<Unit>

    @Operation(summary = "오늘의 이슈 모두 조회")
    @GetMapping
    fun findAllTodayIssues(): ResponseEntity<TodayIssuesResponse>
}
