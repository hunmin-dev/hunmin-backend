package com.bootstrap.subscribe.`in`

import com.bootstrap.helper.IntegrationTest
import com.bootstrap.subscribe.`in`.request.UpdateSubscribeRequest
import com.common.global.auth.role.Role
import com.common.global.auth.token.TokenProvider
import com.domain.subscribe.Subscribe
import com.domain.subscribe.port.out.SubscribeRepositoryPort
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

@IntegrationTest
class SubscribeControllerIntegrationTest(
    @Autowired private val tokenProvider: TokenProvider,
    @Autowired private val subscribeRepositoryPort: SubscribeRepositoryPort,
) {

    var token = tokenProvider.create(id = 1, role = Role.USER)

    @Test
    fun `구독 설정을 업데이트한다`() {
        // given
        subscribeRepositoryPort.save(Subscribe.createSubscribe(1L))

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
            .patch("/subscribes/1")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }

    @Test
    fun `구독 설정을 조회한다`() {
        // given
        subscribeRepositoryPort.save(Subscribe.createSubscribe(1L))

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
