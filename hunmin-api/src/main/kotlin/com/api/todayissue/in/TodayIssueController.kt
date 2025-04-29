package com.api.todayissue.`in`

import com.api.todayissue.`in`.request.TodayIssueCreateRequest
import com.api.todayissue.`in`.response.TodayIssuesResponse
import com.domain.todayissue.port.`in`.TodayIssueUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RequestMapping("/today-issues")
@RestController
class TodayIssueController(
    private val todayIssueUseCase: TodayIssueUseCase
) : TodayIssueApi {

    // TODO : uploadIssue - 기획 구체화 후 셀프 등록은 어떻게 할지 변경 필요함
    @PostMapping
    override fun uploadIssue(@RequestBody request: TodayIssueCreateRequest): ResponseEntity<Unit> {
        val createdTodayIssues = todayIssueUseCase.createTodayIssueManually(request.toCommand())
        return ResponseEntity.created(URI.create("/today-issues/$createdTodayIssues.id"))
            .build()
    }

    @GetMapping
    override fun findAllTodayIssues(): ResponseEntity<TodayIssuesResponse> {
        val todayIssues = todayIssueUseCase.findAllTodayIssues()
        return ResponseEntity.ok(TodayIssuesResponse.from(todayIssues))
    }
}
