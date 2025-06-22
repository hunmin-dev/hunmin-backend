package com.infrastructure.persistence.subscribe

import com.domain.subscribe.Subscribe
import com.domain.subscribe.vo.SubscribeOptions
import com.persistence.subscribe.SubscribeRepositoryAdapter
import com.persistence.subscribe.entity.SubscribeEntity
import com.persistence.subscribe.repository.SubscribeJpaRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class SubscribeRepositoryAdapterTest : BehaviorSpec({

    val subscribeJpaRepository: SubscribeJpaRepository = mockk()
    val subscribeRepositoryAdapter = SubscribeRepositoryAdapter(subscribeJpaRepository)

    Given("구독 정보를 저장할 때") {
        val subscribe = Subscribe(
            id = 1L,
            memberId = 1L,
            options = SubscribeOptions(
                receiveArticleNotifications = true,
                receiveTodayIssueNotifications = true,
                receiveCommentNotifications = true,
                receiveReplyNotifications = true
            ),
            createdAt = 123456789L
        )
        val subscribeEntity = SubscribeEntity(
            id = 1L,
            memberId = 1L,
            receiveArticleNotifications = true,
            receiveTodayIssueNotifications = true,
            receiveCommentNotifications = true,
            receiveReplyNotifications = true,
            createdAt = 123456789L
        )

        When("save 메서드를 호출하면") {
            every { subscribeJpaRepository.save(any()) } returns subscribeEntity

            val result = subscribeRepositoryAdapter.save(subscribe)

            Then("JpaRepository의 save 메서드가 호출되고 결과가 반환된다") {
                verify { subscribeJpaRepository.save(any()) }
                result.id shouldBe 1L
                result.memberId shouldBe 1L
                result.options.receiveArticleNotifications shouldBe true
                result.options.receiveTodayIssueNotifications shouldBe true
                result.options.receiveCommentNotifications shouldBe true
                result.options.receiveReplyNotifications shouldBe true
                result.createdAt shouldBe 123456789L
            }
        }
    }

    Given("userId로 구독 정보를 조회할 때") {
        val subscribeEntity = SubscribeEntity(
            id = 1L,
            memberId = 1L,
            receiveArticleNotifications = true,
            receiveTodayIssueNotifications = true,
            receiveCommentNotifications = true,
            receiveReplyNotifications = true,
            createdAt = 123456789L
        )

        When("findByUserId 메서드를 호출하고 결과가 있으면") {
            every { subscribeJpaRepository.findByUserId(1L) } returns subscribeEntity

            val result = subscribeRepositoryAdapter.findByUserId(1L)

            Then("JpaRepository의 findByUserId 메서드가 호출되고 결과가 반환된다") {
                verify { subscribeJpaRepository.findByUserId(1L) }
                result?.id shouldBe 1L
                result?.memberId shouldBe 1L
                result?.options?.receiveArticleNotifications shouldBe true
                result?.options?.receiveTodayIssueNotifications shouldBe true
                result?.options?.receiveCommentNotifications shouldBe true
                result?.options?.receiveReplyNotifications shouldBe true
                result?.createdAt shouldBe 123456789L
            }
        }

        When("findByUserId 메서드를 호출하고 결과가 없으면") {
            every { subscribeJpaRepository.findByUserId(2L) } returns null

            val result = subscribeRepositoryAdapter.findByUserId(2L)

            Then("JpaRepository의 findByUserId 메서드가 호출되고 null이 반환된다") {
                verify { subscribeJpaRepository.findByUserId(2L) }
                result shouldBe null
            }
        }
    }

    Given("id로 구독 정보를 조회할 때") {
        val subscribeEntity = SubscribeEntity(
            id = 1L,
            memberId = 1L,
            receiveArticleNotifications = true,
            receiveTodayIssueNotifications = true,
            receiveCommentNotifications = true,
            receiveReplyNotifications = true,
            createdAt = 123456789L
        )

        When("findById 메서드를 호출하고 결과가 있으면") {
            every { subscribeJpaRepository.findById(1L) } returns Optional.of(subscribeEntity)

            val result = subscribeRepositoryAdapter.findByIdAndMemberId(1L)

            Then("JpaRepository의 findById 메서드가 호출되고 결과가 반환된다") {
                verify { subscribeJpaRepository.findById(1L) }
                result?.id shouldBe 1L
                result?.memberId shouldBe 1L
                result?.options?.receiveArticleNotifications shouldBe true
                result?.options?.receiveTodayIssueNotifications shouldBe true
                result?.options?.receiveCommentNotifications shouldBe true
                result?.options?.receiveReplyNotifications shouldBe true
                result?.createdAt shouldBe 123456789L
            }
        }

        When("findById 메서드를 호출하고 결과가 없으면") {
            every { subscribeJpaRepository.findById(2L) } returns Optional.empty()

            val result = subscribeRepositoryAdapter.findByIdAndMemberId(2L)

            Then("JpaRepository의 findById 메서드가 호출되고 null이 반환된다") {
                verify { subscribeJpaRepository.findById(2L) }
                result shouldBe null
            }
        }
    }

    Given("userId로 구독 정보 존재 여부를 확인할 때") {
        When("existsByUserId 메서드를 호출하고 결과가 true이면") {
            every { subscribeJpaRepository.existsByUserId(1L) } returns true

            val result = subscribeRepositoryAdapter.existsByUserId(1L)

            Then("JpaRepository의 existsByUserId 메서드가 호출되고 true가 반환된다") {
                verify { subscribeJpaRepository.existsByUserId(1L) }
                result shouldBe true
            }
        }

        When("existsByUserId 메서드를 호출하고 결과가 false이면") {
            every { subscribeJpaRepository.existsByUserId(2L) } returns false

            val result = subscribeRepositoryAdapter.existsByUserId(2L)

            Then("JpaRepository의 existsByUserId 메서드가 호출되고 false가 반환된다") {
                verify { subscribeJpaRepository.existsByUserId(2L) }
                result shouldBe false
            }
        }
    }
})
