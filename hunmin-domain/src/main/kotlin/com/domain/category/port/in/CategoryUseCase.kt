package com.domain.category.port.`in`

import com.domain.category.Category
import com.domain.category.port.`in`.command.CreateCommand
import com.domain.category.port.`in`.command.UpdateCommand

interface CategoryUseCase {
    fun create(
        memberId: Long,
        command: CreateCommand
    ): Category

    fun update(
        memberId: Long,
        categoryId: Long,
        command: UpdateCommand,
    ): Category
}
