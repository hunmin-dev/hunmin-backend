package com.bootstrap.subscribe.`in`

import com.bootstrap.helper.IntegrationTest
import com.bootstrap.subscribe.`in`.request.UpdateSubscribeRequest
import com.common.global.auth.role.Role
import com.common.global.auth.token.TokenProvider
import com.domain.auth.Auth
import com.domain.auth.port.out.AuthPasswordEncryptor
import com.domain.auth.port.out.AuthRepositoryPort
import com.domain.subscribe.Subscribe
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

@IntegrationTest
class SubscribeControllerIntegrationTest(
    @Autowired private val subscribeRepositoryPort: SubscribeRepositoryPort,
    @Autowired private val authRepositoryPort: AuthRepositoryPort,
    @Autowired private val authPasswordEncryptor: AuthPasswordEncryptor,
    @Autowired private val tokenProvider: TokenProvider
) {

    private lateinit var token: String

    @BeforeEach
    fun setup() {
        token = tokenProvider.create(id = 1L, role = Role.USER)
        authRepositoryPort.save(
            Auth.signUpWithEncryption(
                username = "user",
                password = "password",
                authPasswordEncryptor = authPasswordEncryptor,
                role = Role.USER
            )
        )
        subscribeRepositoryPort.save(
            Subscribe.createSubscribe(userId = 1L)
        )
    }

    @Test
    fun `구독 설정을 업데이트한다`() {
        // given
        val request = UpdateSubscribeRequest(
            receiveArticleNotifications = true,
            receiveTodayIssueNotifications = true,
            receiveCommentNotifications = true,
            receiveReplyNotifications = true
        )

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(request)
            .put("/subscribes/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }

    @Test
    fun `구독 설정을 조회한다`() {
        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .header("Authorization", "Bearer $token")
            .get("/subscribes")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }
}
