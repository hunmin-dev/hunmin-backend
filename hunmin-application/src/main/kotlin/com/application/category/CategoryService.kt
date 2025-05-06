package com.application.category

import com.common.global.exceptions.base.CustomException
import com.common.util.throwWhen
import com.domain.category.Category
import com.domain.category.exception.CategoryExceptionType
import com.domain.category.port.`in`.CategoryUseCase
import com.domain.category.port.`in`.command.CreateCommand
import com.domain.category.port.`in`.command.UpdateCommand
import com.domain.category.port.out.CategoryRepositoryPort
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepositoryPort: CategoryRepositoryPort,
) : CategoryUseCase {
    override fun create(memberId: Long, command: CreateCommand): Category {
        throwWhen(categoryRepositoryPort.existsByTitle(command.title)) {
            CustomException(CategoryExceptionType.ALREADY_EXISTS_CATEGORY)
        }

        return categoryRepositoryPort.save(
            Category(
                title = command.title,
                isVisible = command.isVisible,
            ),
        )
    }

    override fun update(
        memberId: Long,
        categoryId: Long,
        command: UpdateCommand,
    ): Category {
        val savedCategory = categoryRepositoryPort.findByCategoryId(categoryId)
                ?: throw CustomException(CategoryExceptionType.CATEGORY_NOT_FOUND)

        command.title?.let {
            throwWhen(categoryRepositoryPort.existsByTitle(it) && it != savedCategory.title) {
                CustomException(CategoryExceptionType.ALREADY_EXISTS_CATEGORY)
            }
        }

        savedCategory.update(title = command.title, isVisible = command.isVisible)

        return savedCategory
    }
}
