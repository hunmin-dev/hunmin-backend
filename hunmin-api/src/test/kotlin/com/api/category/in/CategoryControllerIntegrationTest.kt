package com.api.category.`in`

import com.api.category.`in`.request.CreateRequest
import com.api.category.`in`.request.UpdateRequest
import com.api.helper.IntegrationTest
import com.common.global.auth.role.Role
import com.common.global.auth.token.TokenProvider
import com.domain.category.Category
import com.domain.category.port.out.CategoryRepositoryPort
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

@IntegrationTest
class CategoryControllerIntegrationTest(
    @Autowired private val categoryRepositoryPort: CategoryRepositoryPort,
    @Autowired private val tokenProvider: TokenProvider
) {
    val token = tokenProvider.create(id = 1L, role = Role.ADMIN)

    @Test
    fun `카테고리를 생성한다`() {
        // given
        val request = CreateRequest(title = "initial-title", isVisible = true)

        // when
        val response =
            RestAssured
                .given()
                .log()
                .all()
                .`when`()
                .header("Authorization", "Bearer $token")
                .contentType(ContentType.JSON)
                .body(request)
                .post("/category")
                .then()
                .log()
                .all()
                .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.CREATED.value())
    }

    @Test
    fun `카테고리를 수정한다`() {
        // given
        categoryRepositoryPort.save(
            Category(
                title = "initial-title",
                isVisible = true,
            )
        )
        val request = UpdateRequest(title = "update-title", isVisible = false)

        // when
        val response =
            RestAssured
                .given()
                .log()
                .all()
                .`when`()
                .header("Authorization", "Bearer $token")
                .contentType(ContentType.JSON)
                .body(request)
                .pathParam("categoryId", 1L)
                .patch("/category/{categoryId}")
                .then()
                .log()
                .all()
                .extract()

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value())
    }
}
