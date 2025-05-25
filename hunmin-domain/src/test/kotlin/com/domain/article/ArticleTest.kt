package com.domain.article

import com.common.global.exceptions.base.CustomException
import com.domain.article.exception.ArticleExceptionType.ALREADY_DELETED_ARTICLE
import com.domain.article.vo.ArticleOptions
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.assertj.core.api.Assertions.assertThatThrownBy

class ArticleTest : BehaviorSpec({

    Given("Article을 생성할 때") {

        When("값이 주어지지 않으면") {
            val article = Article(
                title = "글 제목", content = "글 내용",
                writerId = 1L, categoryId = 1L,
                options = ArticleOptions(),
            )

            Then("id와 createdAt은 0이고, 옵션 정보는 초기화된다") {
                assertSoftly {
                    article.id shouldBe 0L
                    article.title shouldBe "글 제목"
                    article.content shouldBe "글 내용"
                    article.writerId shouldBe 1L
                    article.categoryId shouldBe 1L
                    article.options.isVisible shouldBe true
                    article.options.isReported shouldBe false
                    article.options.isTrending shouldBe false
                    article.options.isQuestion shouldBe false
                    article.options.isDeleted shouldBe false
                    article.createdAt shouldBe 0L
                }
            }
        }

        When("createArticle 메서드를 이용하면") {
            val article = Article.createArticle(
                title = "글 제목", content = "글 내용",
                categoryId = 1L, writerId = 1L,
                isVisible = true, isQuestion = false,
            )

            Then("해당 값으로 생성된다") {
                assertSoftly {
                    article.title shouldBe "글 제목"
                    article.content shouldBe "글 내용"
                    article.writerId shouldBe 1L
                    article.categoryId shouldBe 1L
                    article.options.isVisible shouldBe true
                    article.options.isQuestion shouldBe false
                }
            }
        }
    }

    Given("Article의 삭제 여부를 물어볼 때") {
        val article = Article.createArticle(
            title = "글 제목", content = "글 내용",
            categoryId = 1L, writerId = 1L,
            isVisible = true, isQuestion = false,
        )

        When("isDeleted를 호출하면") {
            val articleOptions = article.options

            Then("옵션의 삭제 여부와 같다") {
                article.isDeleted() shouldBe articleOptions.isDeletedState()
            }
        }
    }

    Given("Article을 삭제할 때") {
        val article = Article.createArticle(
            title = "글 제목", content = "글 내용",
            categoryId = 1L, writerId = 1L,
            isVisible = true, isQuestion = false,
        )

        When("삭제되지 않았으면") {
            val deletedArticle = article.delete()

            Then("삭제 처리가 된 Article을 반환하고, 원래의 Article은 삭제되지 않는다") {
                assertSoftly {
                    article.isDeleted() shouldBe false
                    deletedArticle.isDeleted() shouldBe true
                }
            }
        }

        When("이미 삭제되었으면") {
            val deletedArticle = article.delete()

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    deletedArticle.delete()
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ALREADY_DELETED_ARTICLE.message)
            }
        }
    }

    Given("Article을 수정할 때") {
        val article = Article.createArticle(
            title = "글 제목", content = "글 내용",
            categoryId = 1L, writerId = 1L,
            isVisible = true, isQuestion = false,
        )

        When("값이 주어지지 않으면") {
            val updatedArticle = article.update()

            Then("자신의 값과 같은 값을 가진 Article을 반환한다") {
                updatedArticle shouldBeEqualToComparingFields article
            }
        }

        When("값이 주어지면") {
            val updatedArticle = article.update(
                categoryId = 2L,
                title = "글 제목 수정", content = "글 내용 수정",
                isVisible = false,
            )

            Then("id, writerId, createdAt이 같은 Article을 반환한다") {
                assertSoftly {
                    article.id shouldBe updatedArticle.id
                    article.title shouldNotBe updatedArticle.title
                    article.content shouldNotBe updatedArticle.content
                    article.writerId shouldBe updatedArticle.writerId
                    article.categoryId shouldNotBe updatedArticle.categoryId
                    article.options.isVisible shouldNotBe updatedArticle.options.isVisible
                    article.createdAt shouldBe updatedArticle.createdAt
                }
            }
        }
    }
})
