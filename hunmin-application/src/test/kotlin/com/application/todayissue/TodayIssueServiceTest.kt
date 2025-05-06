package com.application.todayissue

import com.common.global.exceptions.base.CustomException
import com.domain.todayissue.TodayIssueFixture.Companion.오늘의_이슈_그룹_생성
import com.domain.todayissue.TodayIssueFixture.Companion.오늘의_이슈_생성_커맨드
import com.domain.todayissue.TodayIssueFixture.Companion.오늘의_이슈_페이징_생성
import com.domain.todayissue.event.TodayIssuesPublishedEvent
import com.domain.todayissue.exception.TodayIssueExceptionType
import com.domain.todayissue.port.out.TodayIssuesRepositoryPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThatThrownBy
import java.time.LocalDateTime

class TodayIssueServiceTest : BehaviorSpec({
    val todayIssuesRepositoryPort: TodayIssuesRepositoryPort = mockk()
    val todayIssueService = TodayIssueService(todayIssuesRepositoryPort)
    val logger = KotlinLogging.logger {}

    Given("오늘의 이슈를 대량 등록할 때") {
        val command = 오늘의_이슈_생성_커맨드()
        val expectedTodayIssues = 오늘의_이슈_그룹_생성()

        When("createTodayIssuesWithBatch를 호출하면") {
            every { todayIssuesRepositoryPort.save(any()) } returns expectedTodayIssues

            val response = todayIssueService.createTodayIssuesWithBatch(listOf(command))

            Then("TodayIssues가 생성되고 이벤트가 발행된다") {
                response.events().size shouldBe 1
                verify { todayIssuesRepositoryPort.save(any()) }
            }
        }
    }

    Given("페이징 조회를 요청할 때") {
        val response = 오늘의_이슈_페이징_생성()

        When("findAllTodayIssuesWithNoOffsetPaging를 호출하면") {
            every { todayIssuesRepositoryPort.findAllTodayIssuesWithNoOffsetPaging(0L, 10) } returns response

            val result = todayIssueService.findAllTodayIssuesWithNoOffsetPaging(0L, 10)

            Then("TodayIssuesSimpleResponse가 반환된다") {
                result shouldBe response
                verify { todayIssuesRepositoryPort.findAllTodayIssuesWithNoOffsetPaging(0L, 10) }
            }
        }
    }

    Given("groupId로 TodayIssues를 조회할 때") {
        val groupId = 1L
        val todayIssues = 오늘의_이슈_그룹_생성()

        When("groupId가 존재하면") {
            every { todayIssuesRepositoryPort.findByIdOrNull(groupId) } returns todayIssues

            val response = todayIssueService.findTodayIssuesByGroupId(groupId)

            Then("TodayIssues가 반환된다") {
                response shouldBe todayIssues
                verify { todayIssuesRepositoryPort.findByIdOrNull(groupId) }
            }
        }

        When("groupId가 존재하지 않으면") {
            every { todayIssuesRepositoryPort.findByIdOrNull(groupId) } returns null

            Then("예외를 발생시킨다") {
                assertThatThrownBy {
                    todayIssueService.findTodayIssuesByGroupId(groupId)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(TodayIssueExceptionType.TODAY_ISSUES_NOT_FOUND_EXCEPTION.message)
            }
        }
    }

    Given("TodayIssuesPublishedEvent가 주어졌을 때") {
        val event = TodayIssuesPublishedEvent(
            todayIssuesId = 1L,
            todayIssues = 오늘의_이슈_그룹_생성().todayIssues,
            createdDate = LocalDateTime.now()
        )

        When("sendEvent를 호출하면") {
            todayIssueService.sendEvent(event)

            Then("이벤트 수신 로그가 출력된다") {
                // 로그는 수동 확인 필요
                logger.debug { "이벤트 수신 로그 확인: $event" }
            }
        }
    }
})
