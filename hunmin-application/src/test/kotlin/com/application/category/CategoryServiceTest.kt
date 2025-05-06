package com.application.category

import com.application.category.CategoryCommandFixture.Companion.카테고리_생성
import com.application.category.CategoryCommandFixture.Companion.카테고리_생성_커맨드
import com.application.category.CategoryCommandFixture.Companion.카테고리_수정_커맨드
import com.common.global.exceptions.base.CustomException
import com.domain.category.exception.CategoryExceptionType.ALREADY_EXISTS_CATEGORY
import com.domain.category.exception.CategoryExceptionType.CATEGORY_NOT_FOUND
import com.domain.category.port.out.CategoryRepositoryPort
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.Instant
import org.assertj.core.api.Assertions.assertThatThrownBy

class CategoryServiceTest : BehaviorSpec({
    val categoryRepositoryPort: CategoryRepositoryPort = mockk()

    val categoryService = CategoryService(categoryRepositoryPort)
    val fixedCreatedAt = Instant.parse("2025-01-01T00:00:00Z")

    Given("카테고리 생성을 할 때") {
        When("제목이 이미 존재하면") {
            every { categoryRepositoryPort.existsByTitle(any()) } returns true

            Then("예외를 발생시킨다") {
                assertThatThrownBy {
                    categoryService.create(1L, 카테고리_생성_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ALREADY_EXISTS_CATEGORY.message)
            }
        }

        When("제목이 존재하지 않으면") {
            every { categoryRepositoryPort.existsByTitle(any()) } returns false
            every { categoryRepositoryPort.save(any()) } returns 카테고리_생성(createdAt = fixedCreatedAt.toEpochMilli())

            val command = 카테고리_생성_커맨드()
            Then("정상 생성되고 카테고리를 반환한다") {
                val response = categoryService.create(1L, command)
                assertSoftly {
                    response.id shouldBe 1L
                    response.title shouldBe "initial-title"
                    response.isVisible shouldBe true
                    response.createdAt shouldBe fixedCreatedAt.toEpochMilli()
                }
            }
        }
    }

    Given("카테고리 수정을 할 때") {
        When("id로 카테고리를 찾을 수 없으면") {
            every { categoryRepositoryPort.findByCategoryId(any()) } returns null

            Then("예외를 발생시킨다") {
                assertThatThrownBy {
                    categoryService.update(memberId = 1L, categoryId = 2L, command = 카테고리_수정_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(CATEGORY_NOT_FOUND.message)
            }
        }

        When("이미 존재하는 다른 카테고리 제목으로 수정하려 하면") {
            every { categoryRepositoryPort.findByCategoryId(any()) } returns 카테고리_생성(createdAt = fixedCreatedAt.toEpochMilli())
            every { categoryRepositoryPort.existsByTitle(any()) } returns true

            Then("예외를 발생시킨다") {
                assertThatThrownBy {
                    categoryService.update(memberId = 1L, categoryId = 1L, command = 카테고리_수정_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ALREADY_EXISTS_CATEGORY.message)
            }
        }

        When("카테고리가 존재하고 다른 카테고리와 이름이 겹치지 않으면") {
            every { categoryRepositoryPort.findByCategoryId(any()) } returns 카테고리_생성(createdAt = fixedCreatedAt.toEpochMilli())
            every { categoryRepositoryPort.existsByTitle(any()) } returns false

            Then("정상 생성되고 카테고리를 반환한다") {
                val response = categoryService.update(memberId = 1L, categoryId = 1L, command = 카테고리_수정_커맨드())
                assertSoftly {
                    response.id shouldBe 1L
                    response.title shouldBe "update-title"
                    response.isVisible shouldBe false
                    response.createdAt shouldBe fixedCreatedAt.toEpochMilli()
                }
            }
        }
    }
})
