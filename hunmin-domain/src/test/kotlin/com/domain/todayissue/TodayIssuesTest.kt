package com.domain.todayissue

import com.domain.todayissue.TodayIssueFixture.Companion.오늘의_이슈_생성
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class TodayIssuesTest : BehaviorSpec({

    Given("issues 목록이 주어질 때") {
        val issues = listOf(오늘의_이슈_생성())

        When("from을 호출하면") {
            val todayIssues = TodayIssues.from(issues)

            Then("생성과 함께 이벤트가 추가된다.") {
                todayIssues.todayIssues shouldBe issues
                todayIssues.events().size shouldBe 1
            }
        }
    }
})
