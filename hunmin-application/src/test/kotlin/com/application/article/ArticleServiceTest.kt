package com.application.article

import com.application.article.ArticleCommandFixture.Companion.일반_글_생성_커맨드
import com.application.auth.AuthCommandFixture.Companion.인증_생성
import com.application.auth.AuthCommandFixture.Companion.인증_생성_id
import com.application.auth.AuthCommandFixture.Companion.인증_생성_어드민_id
import com.application.category.CategoryCommandFixture.Companion.카테고리_생성
import com.common.global.auth.exception.AuthExceptionType.INSUFFICIENT_ROLE
import com.common.global.exceptions.base.CustomException
import com.domain.article.ArticleFixture.Companion.삭제_글_생성_작성자id
import com.domain.article.ArticleFixture.Companion.삭제_글_응답_생성
import com.domain.article.ArticleFixture.Companion.숨김_글_응답_생성_작성자id
import com.domain.article.ArticleFixture.Companion.일반_글_생성
import com.domain.article.ArticleFixture.Companion.일반_글_생성_신고처리
import com.domain.article.ArticleFixture.Companion.일반_글_생성_작성자id
import com.domain.article.ArticleFixture.Companion.일반_글_응답_생성
import com.domain.article.ArticleFixture.Companion.일반_글_페이징_생성
import com.domain.article.exception.ArticleExceptionType.ALREADY_DELETED_ARTICLE
import com.domain.article.exception.ArticleExceptionType.ARTICLE_NOT_FOUND
import com.domain.article.exception.ArticleExceptionType.MISMATCHED_ARTICLE_WRITER
import com.domain.article.port.`in`.command.ReportCommand
import com.domain.article.port.`in`.command.UpdateCommand
import com.domain.article.port.`in`.query.ListArticleFindQuery
import com.domain.article.port.out.ArticleRepositoryPort
import com.domain.auth.exception.AuthExceptionType.AUTH_NOT_FOUND
import com.domain.auth.port.out.AuthRepositoryPort
import com.domain.category.exception.CategoryExceptionType.CATEGORY_NOT_FOUND
import com.domain.category.port.out.CategoryRepositoryPort
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.assertDoesNotThrow

class ArticleServiceTest : BehaviorSpec({

    val articleRepositoryPort: ArticleRepositoryPort = mockk()
    val authRepositoryPort: AuthRepositoryPort = mockk()
    val categoryRepositoryPort: CategoryRepositoryPort = mockk()

    val articleService = ArticleService(articleRepositoryPort, authRepositoryPort, categoryRepositoryPort)

    Given("글 생성을 할 때") {

        When("회원 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.create(writerId = 1L, command = 일반_글_생성_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(AUTH_NOT_FOUND.message)
            }
        }

        When("카테고리 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(1L)
            every { categoryRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.create(writerId = 1L, command = 일반_글_생성_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(CATEGORY_NOT_FOUND.message)
            }
        }

        When("회원 정보와 카테고리 정보가 모두 있으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성()
            every { categoryRepositoryPort.findByIdOrNull(any()) } returns 카테고리_생성(createdAt = 1000)
            every { articleRepositoryPort.save(any()) } returns 일반_글_생성()

            Then("정상 생성된다") {
                val article = articleService.create(writerId = 0L, command = 일반_글_생성_커맨드())

                assertSoftly {
                    article.writerId shouldBe 0L
                    article.title shouldBe "글 제목"
                    article.content shouldBe "글 내용"
                    article.categoryId shouldBe 1L
                    article.options.isVisible shouldBe true
                    article.options.isQuestion shouldBe false
                }
            }
        }
    }

    Given("글 삭제를 할 때") {

        When("회원 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.create(writerId = 0L, command = 일반_글_생성_커맨드())
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(AUTH_NOT_FOUND.message)
            }
        }

        When("글 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(0L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.delete(memberId = 0L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("일반 유저이면서 작성자가 아니면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성()

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.delete(memberId = 2L, articleId = 0L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(MISMATCHED_ARTICLE_WRITER.message)
            }
        }

        When("이미 삭제된 글이면 작성자더라도") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 삭제_글_생성_작성자id(2L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.delete(memberId = 2L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("이미 삭제된 글이면 어드민이더라도") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 삭제_글_생성_작성자id(2L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.delete(memberId = 2L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ALREADY_DELETED_ARTICLE.message)
            }
        }

        When("일반 유저이면서 작성자고, 삭제되지 않았으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성_작성자id(2L)

            Then("삭제 처리 시 예외가 발생하지 않는다") {
                assertDoesNotThrow { articleService.delete(memberId = 2L, articleId = 0L) }
            }
        }

        When("어드민이면서 작성자가 아니고, 삭제되지 않았으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성()

            Then("삭제 처리 시 예외가 발생하지 않는다") {
                assertDoesNotThrow { articleService.delete(memberId = 2L, articleId = 0L) }
            }
        }
    }

    Given("글 수정을 할 때") {

        When("변경할 대상의 카테고리가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(1L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성_작성자id(1L)
            every { categoryRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.update(memberId = 1L, articleId = 1L, command = UpdateCommand(categoryId = 2L))
                }
            }
        }

        When("회원 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.update(memberId = -1L, articleId = 1L, command = UpdateCommand(title = "수정 글"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(AUTH_NOT_FOUND.message)
            }
        }

        When("글 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(1L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.update(memberId = 1L, articleId = 1L, command = UpdateCommand(title = "수정 글"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("일반 유저이면서 작성자가 아니면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성_작성자id(1L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.update(memberId = 2L, articleId = 1L, command = UpdateCommand(title = "수정 글"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(MISMATCHED_ARTICLE_WRITER.message)
            }
        }

        When("작성자이면서 삭제된 상태면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(1L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 삭제_글_생성_작성자id(1L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.update(memberId = 1L, articleId = 1L, command = UpdateCommand(title = "수정 글"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("작성자이면서 삭제된 상태가 아니면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(1L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성_작성자id(1L)

            Then("예외가 발생하지 않는다") {
                assertDoesNotThrow { articleService.update(memberId = 1L, articleId = 1L, command = UpdateCommand(title = "수정 글")) }
            }
        }

        When("어드민이면서 작성자가 아니면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(1L)
            every { articleRepositoryPort.findByIdOrNull((any())) } returns 일반_글_생성_작성자id(2L)

            Then("예외가 발생하지 않는다") {
                assertDoesNotThrow { articleService.update(memberId = 1L, articleId = 1L, command = UpdateCommand(title = "수정 글")) }
            }
        }

        When("어드민이고 삭제된 상태면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(1L)
            every { articleRepositoryPort.findByIdOrNull((any())) } returns 삭제_글_생성_작성자id(2L)

            Then("예외가 발생하지 않는다") {
                assertDoesNotThrow { articleService.update(memberId = 1L, articleId = 1L, command = UpdateCommand(title = "수정 글")) }
            }
        }
    }

    Given("글 신고를 할 때") {

        When("회원 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.report(memberId = -1L, articleId = 0L, command = ReportCommand(reason = "신고 들어옴"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(AUTH_NOT_FOUND.message)
            }
        }

        When("글 정보가 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(1L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns null

            Then("어드민이더라도 예외가 발생한다") {
                assertThatThrownBy {
                    articleService.report(memberId = 1L, articleId = 1L, command = ReportCommand(reason = "신고 들어옴"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("어드민이 아니고 일반 유저면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성()

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.report(memberId = 2L, articleId = 0L, command = ReportCommand(reason = "신고 들어옴"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(MISMATCHED_ARTICLE_WRITER.message)
            }
        }

        When("어드민이 아니고 작성자면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성_작성자id(2L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.report(memberId = 2L, articleId = 0L, command = ReportCommand(reason = "신고 들어옴"))
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(INSUFFICIENT_ROLE.message)
            }
        }

        When("어드민이면서 작성자가 아니면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(2L)
            every { articleRepositoryPort.findByIdOrNull(any()) } returns 일반_글_생성()
            every { articleRepositoryPort.save(any()) } returns 일반_글_생성_신고처리()

            Then("신고 처리 시 예외가 발생하지 않는다") {
                assertDoesNotThrow { articleService.report(memberId = 2L, articleId = 0L, command = ReportCommand(reason = "신고 들어옴")) }
            }
        }
    }

    Given("글 단건 조회를 할 때") {

        When("익명 / 일반 유저 / 작성자 / 어드민 상관없이, 글 정보가 아예 없으면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null
            every { articleRepositoryPort.findArticleById(any()) } throws CustomException(ARTICLE_NOT_FOUND)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.find(memberId = -1L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("익명일 때, 삭제된 상태면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null
            every { articleRepositoryPort.findArticleById(any()) } returns 삭제_글_응답_생성()

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.find(memberId = -1L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("어드민이 아닐 때, 작성자 / 일반 유저더라도 삭제된 상태면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(1L)
            every { articleRepositoryPort.findArticleById(any()) } returns 삭제_글_응답_생성()

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.find(memberId = 1L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("익명일 때, 숨김 처리된 글이면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null
            every { articleRepositoryPort.findArticleById(any()) } returns 숨김_글_응답_생성_작성자id(2L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.find(memberId = -1L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("일반 유저일 때, 숨김 처리된 글이면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(2L)
            every { articleRepositoryPort.findArticleById(any()) } returns 숨김_글_응답_생성_작성자id(2L)

            Then("예외가 발생한다") {
                assertThatThrownBy {
                    articleService.find(memberId = 1L, articleId = 1L)
                }.isInstanceOf(CustomException::class.java)
                    .hasMessageContaining(ARTICLE_NOT_FOUND.message)
            }
        }

        When("익명 / 일반 유저일 때, 글이 존재하고 볼 수 있는 상태면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns null
            every { articleRepositoryPort.findArticleById(any()) } returns 일반_글_응답_생성()

            Then("조회 가능하다") {
                val article = articleService.find(memberId = -1L, articleId = 1L)

                assertSoftly {
                    article.title shouldBe "일반 글"
                    article.content shouldBe "일반 글 내용"
                    article.isVisible shouldBe true
                    article.isDeleted shouldBe false
                }
            }
        }

        When("작성자일 때, 숨김 처리된 글이면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_id(1L)
            every { articleRepositoryPort.findArticleById(any()) } returns 숨김_글_응답_생성_작성자id(1L)

            Then("조회 가능하다") {
                val article = articleService.find(memberId = 1L, articleId = 1L)

                assertSoftly {
                    article.title shouldBe "숨김 글"
                    article.content shouldBe "숨김 글 내용"
                    article.isVisible shouldBe false
                    article.isDeleted shouldBe false
                }
            }
        }

        When("어드민일 때, 숨김 처리된 글이면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(2L)
            every { articleRepositoryPort.findArticleById(any()) } returns 숨김_글_응답_생성_작성자id(1L)

            Then("조회 가능하다") {
                val article = articleService.find(memberId = 2L, articleId = 1L)

                assertSoftly {
                    article.title shouldBe "숨김 글"
                    article.content shouldBe "숨김 글 내용"
                    article.isVisible shouldBe false
                    article.isDeleted shouldBe false
                }
            }
        }

        When("어드민일 때, 삭제된 글이면") {
            every { authRepositoryPort.findByIdOrNull(any()) } returns 인증_생성_어드민_id(2L)
            every { articleRepositoryPort.findArticleById(any()) } returns 삭제_글_응답_생성()

            Then("조회 가능하다") {
                val article = articleService.find(memberId = 2L, articleId = 1L)

                assertSoftly {
                    article.title shouldBe "삭제 글"
                    article.content shouldBe "삭제 글 내용"
                    article.isVisible shouldBe false
                    article.isDeleted shouldBe true
                }
            }
        }
    }

    Given("글 목록 조회를 할 때") {
        val response = 일반_글_페이징_생성()

        When("findArticlesWithNoOffsetPaging를 호출하면") {
            every { articleRepositoryPort.findArticleByQuery(0L, 10, listOf(1L), any(), any(), any(), any()) } returns response
            val query = ListArticleFindQuery(lastArticleId = 0L, size = 10, categories = listOf(1L), sort = "createdAt", direction = "DESC", isQuestion = false, isTrending = true)
            val result = articleService.findArticlesWithNoOffsetPaging(1L, query)

            Then("ArticlesSimpleResponse가 반환된다") {
                result shouldBe response
                verify { articleRepositoryPort.findArticleByQuery(lastArticleId = 0L, size = 10, categories = listOf(1L), sort = "createdAt", direction = "DESC", isQuestion = false, isTrending = true) }
            }
        }
    }
})
