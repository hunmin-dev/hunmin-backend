package com.domain.category

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CategoryTest : BehaviorSpec({

    Given("Category를 생성할 때") {

        When("값이 주어지지 않으면") {
            Then("id와 createdAt, createdBy, updatedBy는 0이고, isVisible은 true 이다") {
                val category = Category(title = "initial-title")

                assertSoftly {
                    category.id shouldBe 0L
                    category.title shouldBe "initial-title"
                    category.isVisible shouldBe true
                    category.createdAt shouldBe 0L
                }
            }
        }

        When("값이 명시적으로 주어지면") {
            Then("해당 값으로 생성된다") {
                val category = Category(id = 1L, title = "initial-title", isVisible = false, createdAt = 1000L, createdBy = 1L, updatedBy = 1L)

                assertSoftly {
                    category.id shouldBe 1L
                    category.title shouldBe "initial-title"
                    category.isVisible shouldBe false
                    category.createdAt shouldBe 1000L
                    category.createdBy shouldBe 1L
                    category.updatedBy shouldBe 1L
                }
            }
        }
    }

    Given("기존 Category가 존재할 때") {
        val category = Category(id = 1L, title = "initial-title", isVisible = true, createdAt = 1000L, createdBy = 1L, updatedBy = 1L)

        When("수정 값이 주어지지 않으면") {
            Then("기존 값이 그대로 유지된다") {
                category.update()

                assertSoftly {
                    category.id shouldBe 1L
                    category.title shouldBe "initial-title"
                    category.isVisible shouldBe true
                    category.createdAt shouldBe 1000L
                    category.createdBy shouldBe 1L
                    category.updatedBy shouldBe 1L
                }
            }
        }

        When("값이 주어지면") {
            Then("해당 값으로 수정된다") {
                category.update(title = "updated-title", isVisible = false)

                assertSoftly {
                    category.id shouldBe 1L
                    category.title shouldBe "updated-title"
                    category.isVisible shouldBe false
                    category.createdAt shouldBe 1000L
                    category.createdBy shouldBe 1L
                }
            }
        }
    }
})
