package com.application.subscribe

import com.common.global.exceptions.base.CustomException
import com.domain.subscribe.Subscribe
import com.domain.subscribe.exception.SubscribeExceptionType
import com.domain.subscribe.port.`in`.command.UpdateSubscribeCommand
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import com.domain.subscribe.vo.SubscribeOptions
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThatThrownBy

class SubscribeServiceTest : BehaviorSpec({

    val subscribeRepositoryPort: SubscribeRepositoryPort = mockk()

    val subscribeService = SubscribeService(subscribeRepositoryPort)
    val logger = KotlinLogging.logger {}

    Given("구독 업데이트를 할 때") {

        When("구독 정보가 없으면") {
            every { subscribeRepositoryPort.findByIdAndMemberId(any(), any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    subscribeService.update(
                        memberId = 1L,
                        subscribeId = 1L,
                        command = UpdateSubscribeCommand(null, null, null, null)
                    )
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(SubscribeExceptionType.SUBSCRIBE_NOT_FOUND.message)
            }
        }

        When("알림 구독 정보가 정상적으로 존재할 때") {
            every { subscribeRepositoryPort.findByIdAndMemberId(any(), any()) } returns Subscribe(
                id = 1L,
                memberId = 1L,
                options = SubscribeOptions(),
                createdAt = 123456789L
            )
            every { subscribeRepositoryPort.save(any()) } returns Subscribe(
                id = 1L,
                memberId = 1L,
                options = SubscribeOptions(
                    receiveArticleNotifications = false,
                    receiveTodayIssueNotifications = false,
                    receiveCommentNotifications = false,
                    receiveReplyNotifications = false
                ),
                createdAt = 123456789L
            )

            Then("업데이트된 구독 정보를 반환한다") {
                val subscribe = subscribeService.update(
                    memberId = 1L,
                    subscribeId = 1L,
                    command = UpdateSubscribeCommand(
                        receiveArticleNotifications = false,
                        receiveTodayIssueNotifications = false,
                        receiveCommentNotifications = false,
                        receiveReplyNotifications = false
                    )
                )

                assertSoftly {
                    subscribe.id shouldBe 1L
                    subscribe.memberId shouldBe 1L
                    subscribe.options.receiveArticleNotifications shouldBe false
                    subscribe.options.receiveTodayIssueNotifications shouldBe false
                    subscribe.options.receiveCommentNotifications shouldBe false
                    subscribe.options.receiveReplyNotifications shouldBe false
                }
            }
        }
    }

    Given("구독 조회를 할 때") {

        When("회원 정보가 없으면") {
            every { subscribeRepositoryPort.findByMemberId(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    subscribeService.findByMemberId(memberId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(SubscribeExceptionType.SUBSCRIBE_NOT_FOUND.message)
            }
        }

        When("회원 정보가 있으면") {
            every { subscribeRepositoryPort.findByMemberId(any()) } returns Subscribe(
                id = 1L,
                memberId = 1L,
                options = SubscribeOptions(),
                createdAt = 123456789L
            )

            Then("구독 정보를 반환한다") {
                val subscribe = subscribeService.findByMemberId(memberId = 1L)

                assertSoftly {
                    subscribe.id shouldBe 1L
                    subscribe.memberId shouldBe 1L
                    subscribe.options.receiveArticleNotifications shouldBe false
                    subscribe.options.receiveTodayIssueNotifications shouldBe false
                    subscribe.options.receiveCommentNotifications shouldBe false
                    subscribe.options.receiveReplyNotifications shouldBe false
                }
            }
        }
    }
})
