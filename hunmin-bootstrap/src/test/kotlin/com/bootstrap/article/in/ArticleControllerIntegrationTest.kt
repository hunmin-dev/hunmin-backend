package com.bootstrap.article.`in`

import com.bootstrap.article.`in`.request.CreateArticleRequest
import com.bootstrap.article.`in`.request.ReportArticleRequest
import com.bootstrap.article.`in`.request.UpdateArticleRequest
import com.bootstrap.helper.IntegrationTest
import com.common.global.auth.role.Role
import com.common.global.auth.token.TokenProvider
import com.domain.article.Article
import com.domain.article.port.out.ArticleRepositoryPort
import com.domain.auth.Auth
import com.domain.auth.port.out.AuthPasswordEncryptor
import com.domain.auth.port.out.AuthRepositoryPort
import com.domain.category.Category
import com.domain.category.port.out.CategoryRepositoryPort
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

@IntegrationTest
class ArticleControllerIntegrationTest(
    @Autowired private val authRepositoryPort: AuthRepositoryPort,
    @Autowired private val authPasswordEncryptor: AuthPasswordEncryptor,
    @Autowired private val categoryRepositoryPort: CategoryRepositoryPort,
    @Autowired private val articleRepositoryPort: ArticleRepositoryPort,
    @Autowired private val tokenProvider: TokenProvider
) {
    val token = tokenProvider.create(id = 1L, role = Role.ADMIN)

    @Test
    fun `지식 공유 글을 생성한다`() {
        // given
        authRepositoryPort.save(Auth.signUpWithEncryption(username = "admin", password = "password", authPasswordEncryptor = authPasswordEncryptor, role = Role.ADMIN))
        categoryRepositoryPort.save(Category(title = "카테고리", isVisible = true))
        val request = CreateArticleRequest(categoryId = 1L, title = "제목", content = "본문", isVisible = true, isQuestion = false)

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(request)
            .post("/articles")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.CREATED.value())
    }

    @Test
    fun `지식 공유 글을 단건 조회한다`() {
        // given
        authRepositoryPort.save(Auth.signUpWithEncryption(username = "admin", password = "password", authPasswordEncryptor = authPasswordEncryptor, role = Role.ADMIN))
        categoryRepositoryPort.save(Category(title = "카테고리", isVisible = true))
        articleRepositoryPort.save(Article.createArticle(title = "제목", content = "글", categoryId = 1L, writerId = 1L, isVisible = true, isQuestion = false))

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .get("/articles/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }

    @Test
    fun `지식 공유 글을 신고한다`() {
        // given
        authRepositoryPort.save(Auth.signUpWithEncryption(username = "admin", password = "password", authPasswordEncryptor = authPasswordEncryptor, role = Role.ADMIN))
        categoryRepositoryPort.save(Category(title = "카테고리", isVisible = true))
        articleRepositoryPort.save(Article.createArticle(title = "제목", content = "글", categoryId = 1L, writerId = 1L, isVisible = true, isQuestion = false))
        val request = ReportArticleRequest(reason = "글 신고")

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(request)
            .post("/articles/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.NO_CONTENT.value())
    }

    @Test
    fun `지식 공유 글을 삭제한다`() {
        // given
        authRepositoryPort.save(Auth.signUpWithEncryption(username = "admin", password = "password", authPasswordEncryptor = authPasswordEncryptor, role = Role.ADMIN))
        categoryRepositoryPort.save(Category(title = "카테고리", isVisible = true))
        articleRepositoryPort.save(Article.createArticle(title = "제목", content = "글", categoryId = 1L, writerId = 1L, isVisible = true, isQuestion = false))

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .delete("/articles/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.NO_CONTENT.value())
    }

    @Test
    fun `지식 공유 글을 수정한다`() {
        // given
        authRepositoryPort.save(Auth.signUpWithEncryption(username = "admin", password = "password", authPasswordEncryptor = authPasswordEncryptor, role = Role.ADMIN))
        categoryRepositoryPort.save(Category(title = "카테고리", isVisible = true))
        articleRepositoryPort.save(Article.createArticle(title = "제목", content = "글", categoryId = 1L, writerId = 1L, isVisible = true, isQuestion = false))
        val request = UpdateArticleRequest(title = "제목 수정")

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(request)
            .patch("/articles/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }

    @Test
    fun `지식 공유 글을 no-offset 페이징 조회를 한다`() {
        // given
        authRepositoryPort.save(Auth.signUpWithEncryption(username = "admin", password = "password", authPasswordEncryptor = authPasswordEncryptor, role = Role.ADMIN))
        categoryRepositoryPort.save(Category(title = "카테고리 1", isVisible = true))
        categoryRepositoryPort.save(Category(title = "카테고리 2", isVisible = true))
        articleRepositoryPort.save(Article.createArticle(title = "제목 1", content = "글 1", categoryId = 1L, writerId = 1L, isVisible = true, isQuestion = false))
        articleRepositoryPort.save(Article.createArticle(title = "제목 2", content = "글 2", categoryId = 1L, writerId = 1L, isVisible = true, isQuestion = false))
        articleRepositoryPort.save(Article.createArticle(title = "제목 3", content = "글 3", categoryId = 2L, writerId = 1L, isVisible = true, isQuestion = false))

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .queryParam("categories", "1")
            .get("/articles")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }
}