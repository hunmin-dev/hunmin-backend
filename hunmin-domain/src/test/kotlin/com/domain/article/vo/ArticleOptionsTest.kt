package com.domain.article.vo

import com.common.global.exceptions.base.CustomException
import com.domain.article.exception.ArticleExceptionType.ALREADY_DELETED_ARTICLE
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.assertj.core.api.Assertions.assertThatThrownBy

class ArticleOptionsTest : BehaviorSpec({

    Given("ArticleOptions를 생성할 때") {

        When("값이 주어지지 않으면") {
            val articleOptions = ArticleOptions()

            Then("기본값을 따른다") {
                assertSoftly {
                    articleOptions.isVisible shouldBe true
                    articleOptions.isReported shouldBe false
                    articleOptions.isTrending shouldBe false
                    articleOptions.isQuestion shouldBe false
                    articleOptions.isDeleted shouldBe false
                }
            }
        }

        When("createOptions 메서드를 이용하면") {
            val articleOptions = ArticleOptions.createOptions(isVisible = true, isQuestion = true)

            Then("해당 값을 이용하고 isReported, isTrending, isDeleted는 false 이다") {
                assertSoftly {
                    articleOptions.isVisible shouldBe true
                    articleOptions.isReported shouldBe false
                    articleOptions.isTrending shouldBe false
                    articleOptions.isQuestion shouldBe true
                    articleOptions.isDeleted shouldBe false
                }
            }
        }
    }

    Given("ArticleOptions의 삭제 여부를 물어볼 때") {
        val articleOptions = ArticleOptions.createOptions(isVisible = true, isQuestion = false)

        When("isDeletedState 메서드를 이용하면") {

            Then("삭제 여부를 알 수 있다") {
                assertSoftly {
                    articleOptions.isDeleted shouldBe false
                    articleOptions.isDeletedState() shouldBe false
                }
            }
        }
    }

    Given("ArticleOptions를 삭제할 때") {
        val articleOptions = ArticleOptions.createOptions(isVisible = true, isQuestion = false)

        When("삭제되지 않았으면") {
            val deletedArticleOptions = articleOptions.delete()

            Then("삭제 처리가 된 ArticleOptions를 반환하고, 원래의 삭제 여부는 변경되지 않는다") {
                assertSoftly {
                    articleOptions.isDeletedState() shouldBe false
                    deletedArticleOptions.isDeletedState() shouldBe true
                }
            }
        }

        When("이미 삭제되었으면") {
            val deletedArticleOptions = articleOptions.delete()

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    deletedArticleOptions.delete()
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ALREADY_DELETED_ARTICLE.message)
            }
        }
    }

    Given("ArticleOptions의 신고 여부를 변경할 때") {
        val articleOptions = ArticleOptions.createOptions(isVisible = true, isQuestion = false)

        When("reportState가 주어지면") {
            val deletedArticleOptions = articleOptions.updateReport(reportState = true)

            Then("해당 값으로 신고 여부가 설정된 ArticleOptions를 반환하고, 원래의 신고 여부는 변경되지 않는다") {
                assertSoftly {
                    deletedArticleOptions.isReported shouldBe true
                    articleOptions.isReported shouldNotBe deletedArticleOptions.isReported
                }
            }
        }
    }

    Given("ArticleOptions의 노출 여부를 변경할 때") {
        val articleOptions = ArticleOptions.createOptions(isVisible = true, isQuestion = false)

        When("값이 주어지지 않으면") {
            val updatedArticleOptions = articleOptions.updateVisible()

            Then("기존의 노출 여부와 같은 값을 가진 ArticleOptions를 반환한다") {
                assertSoftly {
                    articleOptions.isVisible shouldBe true
                    updatedArticleOptions.isVisible shouldBe true
                }
            }
        }

        When("값이 주어지면") {
            val updatedArticleOptions = articleOptions.updateVisible(isVisible = false)

            Then("기존의 노출 여부와 반대 값을 가진 ArticleOptions를 반환하고, 기존의 노출 여부는 유지된다") {
                assertSoftly {
                    articleOptions.isVisible shouldBe true
                    updatedArticleOptions.isVisible shouldBe false
                }
            }
        }
    }
})
