package com.api.todayissue.`in`

import com.api.helper.IntegrationTest
import com.application.todayissue.TodayIssueService
import com.domain.todayissue.TodayIssueFixture.Companion.오늘의_이슈_생성_커맨드
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

@IntegrationTest
class TodayIssueControllerIntegrationTest(
    @Autowired private val todayIssueService: TodayIssueService,
) {

    @Test
    fun `오늘의 이슈 no-offset 페이징 조회를 한다`() {
        // when
        val response = RestAssured.given().log()
            .all()
            .`when`()
            .get("/today-issues")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }

    @Test
    fun `오늘의 이슈를 그룹id로 조회한다`() {
        // given
        val issues = todayIssueService.createTodayIssuesWithBatch(listOf(오늘의_이슈_생성_커맨드()))

        // when
        val response = RestAssured.given().log()
            .all()
            .`when`()
            .get("/today-issues/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }
}
