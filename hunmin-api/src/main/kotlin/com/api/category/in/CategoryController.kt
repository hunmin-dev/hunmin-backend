package com.api.category.`in`

import com.api.category.`in`.request.CreateRequest
import com.api.category.`in`.request.UpdateRequest
import com.api.category.`in`.response.CategoryResponse
import com.common.global.auth.annotation.AuthMember
import com.common.global.auth.role.Role
import com.domain.category.port.`in`.CategoryUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/category")
@RestController
class CategoryController(
    private val categoryUseCase: CategoryUseCase,
) : CategoryApi {
    @PostMapping
    override fun create(
        @AuthMember(requiredRole = Role.ADMIN) memberId: Long,
        request: CreateRequest
    ): ResponseEntity<CategoryResponse> {
        val category = categoryUseCase.create(memberId = memberId, command = request.toCommand())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                CategoryResponse(
                    id = category.id,
                    title = category.title,
                    isVisible = category.isVisible,
                    createdAt = category.createdAt,
                ),
            )
    }

    @PatchMapping("/{categoryId}")
    override fun update(
        @AuthMember(requiredRole = Role.ADMIN) memberId: Long,
        categoryId: Long,
        request: UpdateRequest,
    ): ResponseEntity<CategoryResponse> {
        val updatedCategory = categoryUseCase.update(
            memberId = memberId,
            categoryId = categoryId,
            command = request.toCommand()
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                CategoryResponse(
                    id = categoryId,
                    title = updatedCategory.title,
                    isVisible = updatedCategory.isVisible,
                    createdAt = updatedCategory.createdAt,
                ),
            )
    }
}
