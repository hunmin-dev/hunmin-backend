package com.domain.subscribe

import com.domain.subscribe.vo.SubscribeOptions
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class SubscribeTest : BehaviorSpec({

    Given("Subscribe를 생성할 때") {

        When("createSubscribe 메서드를 이용하면") {
            val subscribe = Subscribe.createSubscribe(memberId = 1L)

            Then("기본 값으로 생성된다") {
                assertSoftly {
                    subscribe.memberId shouldBe 1L
                    subscribe.options.receiveArticleNotifications shouldBe false
                    subscribe.options.receiveTodayIssueNotifications shouldBe false
                    subscribe.options.receiveCommentNotifications shouldBe false
                    subscribe.options.receiveReplyNotifications shouldBe false
                }
            }
        }
    }

    Given("Subscribe를 수정할 때") {
        val subscribe = Subscribe.createSubscribe(memberId = 1L)

        When("값이 주어지지 않으면") {
            val updatedSubscribe = subscribe.update(null, null, null, null)

            Then("자신의 값과 같은 값을 가진 Subscribe를 반환한다") {
                assertSoftly {
                    updatedSubscribe.id shouldBe subscribe.id
                    updatedSubscribe.memberId shouldBe subscribe.memberId
                    updatedSubscribe.options.receiveArticleNotifications shouldBe subscribe.options.receiveArticleNotifications
                    updatedSubscribe.options.receiveTodayIssueNotifications shouldBe subscribe.options.receiveTodayIssueNotifications
                    updatedSubscribe.options.receiveCommentNotifications shouldBe subscribe.options.receiveCommentNotifications
                    updatedSubscribe.options.receiveReplyNotifications shouldBe subscribe.options.receiveReplyNotifications
                    updatedSubscribe.createdAt shouldBe subscribe.createdAt
                }
            }
        }

        When("값이 주어지면") {
            val updatedOptions = SubscribeOptions.createDefaultOptions()
            val updatedSubscribe = subscribe.update(true, true, true, true)

            Then("id, memberId, createdAt이 같은 Subscribe를 반환한다") {
                assertSoftly {
                    updatedSubscribe.id shouldBe subscribe.id
                    updatedSubscribe.memberId shouldBe subscribe.memberId
                    updatedSubscribe.options.receiveArticleNotifications shouldNotBe subscribe.options.receiveArticleNotifications
                    updatedSubscribe.options.receiveTodayIssueNotifications shouldNotBe subscribe.options.receiveTodayIssueNotifications
                    updatedSubscribe.options.receiveCommentNotifications shouldNotBe subscribe.options.receiveCommentNotifications
                    updatedSubscribe.options.receiveReplyNotifications shouldNotBe subscribe.options.receiveReplyNotifications
                    updatedSubscribe.createdAt shouldBe subscribe.createdAt
                }
            }
        }
    }
})
