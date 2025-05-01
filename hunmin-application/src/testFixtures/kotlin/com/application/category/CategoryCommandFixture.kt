package com.application.category

import com.domain.category.Category
import com.domain.category.port.`in`.command.CreateCommand
import com.domain.category.port.`in`.command.UpdateCommand

class CategoryCommandFixture {
    companion object {
        fun 카테고리_생성(createdAt: Long): Category =
            Category(
                id = 1L,
                title = "initial-title",
                isVisible = true,
                createdAt = createdAt,
                createdBy = 1L,
                updatedBy = 1L,
            )

        fun 카테고리_생성_커맨드(): CreateCommand =
            CreateCommand(
                title = "initial-title",
                isVisible = true,
            )

        fun 카테고리_수정_커맨드(): UpdateCommand =
            UpdateCommand(
                title = "update-title",
                isVisible = false,
            )
    }
}
