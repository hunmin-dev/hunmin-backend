package com.bootstrap.auth.`in`

import com.bootstrap.auth.`in`.request.SignInRequest
import com.bootstrap.auth.`in`.request.SignUpRequest
import com.bootstrap.helper.IntegrationTest
import com.common.global.auth.role.Role
import com.domain.auth.Auth
import com.domain.auth.port.out.AuthPasswordEncryptor
import com.domain.auth.port.out.AuthRepositoryPort
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

@IntegrationTest
class AuthControllerIntegrationTest(
    @Autowired private val authRepositoryPort: AuthRepositoryPort,
    @Autowired private val authPasswordEncryptor: AuthPasswordEncryptor,
) {
    @Test
    fun `회원가입을 진행한다`() {
        // given
        val request = SignUpRequest("username", "password")

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/auth/sign-up")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.CREATED.value())
    }

    @Test
    fun `로그인을 진행한다`() {
        // given
        authRepositoryPort.save(
            Auth.signUpWithEncryption(
                username = "username",
                password = "password",
                authPasswordEncryptor = authPasswordEncryptor,
                role = Role.USER,
            ),
        )
        val request = SignInRequest("username", "password")

        // when
        val response = RestAssured.given()
            .log()
            .all()
            .`when`()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/auth/sign-in")
            .then()
            .log()
            .all()
            .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }
}
