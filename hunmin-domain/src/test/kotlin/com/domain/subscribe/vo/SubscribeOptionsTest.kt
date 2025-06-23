package com.domain.subscribe.vo

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SubscribeOptionsTest : BehaviorSpec({

    Given("SubscribeOptions를 생성할 때") {

        When("값이 주어지지 않으면") {
            val subscribeOptions = SubscribeOptions()

            Then("기본값을 따른다") {
                assertSoftly {
                    subscribeOptions.receiveArticleNotifications shouldBe false
                    subscribeOptions.receiveTodayIssueNotifications shouldBe false
                    subscribeOptions.receiveCommentNotifications shouldBe false
                    subscribeOptions.receiveReplyNotifications shouldBe false
                }
            }
        }

        When("createOptions 메서드를 이용하면") {
            val subscribeOptions = SubscribeOptions.createDefaultOptions()
            val afterOption = subscribeOptions.updateOptions(true, true, true, true)

            Then("해당 값을 이용한다") {
                assertSoftly {
                    afterOption.receiveArticleNotifications shouldBe true
                    afterOption.receiveTodayIssueNotifications shouldBe true
                    afterOption.receiveCommentNotifications shouldBe true
                    afterOption.receiveReplyNotifications shouldBe true
                }
            }
        }
    }

    Given("SubscribeOptions를 업데이트할 때") {
        val subscribeOptions = SubscribeOptions.createDefaultOptions()

        When("값이 주어지지 않으면") {
            val updatedSubscribeOptions = subscribeOptions.updateOptions(null, null, null, null)

            Then("기존의 값과 같은 값을 가진 SubscribeOptions를 반환한다") {
                assertSoftly {
                    updatedSubscribeOptions.receiveArticleNotifications shouldBe subscribeOptions.receiveArticleNotifications
                    updatedSubscribeOptions.receiveTodayIssueNotifications shouldBe subscribeOptions.receiveTodayIssueNotifications
                    updatedSubscribeOptions.receiveCommentNotifications shouldBe subscribeOptions.receiveCommentNotifications
                    updatedSubscribeOptions.receiveReplyNotifications shouldBe subscribeOptions.receiveReplyNotifications
                }
            }
        }

        When("일부 값만 주어지면") {
            val updatedSubscribeOptions = subscribeOptions.updateOptions(
                receiveArticleNotifications = true,
                receiveTodayIssueNotifications = null,
                receiveCommentNotifications = null,
                receiveReplyNotifications = null
            )

            Then("주어진 값만 업데이트된 SubscribeOptions를 반환한다") {
                assertSoftly {
                    updatedSubscribeOptions.receiveArticleNotifications shouldBe true
                    updatedSubscribeOptions.receiveTodayIssueNotifications shouldBe subscribeOptions.receiveTodayIssueNotifications
                    updatedSubscribeOptions.receiveCommentNotifications shouldBe subscribeOptions.receiveCommentNotifications
                    updatedSubscribeOptions.receiveReplyNotifications shouldBe subscribeOptions.receiveReplyNotifications
                }
            }
        }

        When("모든 값이 주어지면") {
            val updatedSubscribeOptions = subscribeOptions.updateOptions(
                receiveArticleNotifications = false,
                receiveTodayIssueNotifications = false,
                receiveCommentNotifications = false,
                receiveReplyNotifications = false
            )

            Then("모든 값이 업데이트된 SubscribeOptions를 반환한다") {
                assertSoftly {
                    updatedSubscribeOptions.receiveArticleNotifications shouldBe false
                    updatedSubscribeOptions.receiveTodayIssueNotifications shouldBe false
                    updatedSubscribeOptions.receiveCommentNotifications shouldBe false
                    updatedSubscribeOptions.receiveReplyNotifications shouldBe false
                }
            }
        }
    }
})
