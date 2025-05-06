package com.domain.category.port.out

import com.domain.category.Category

interface CategoryRepositoryPort {
    fun save(category: Category): Category

    fun findByCategoryId(categoryId: Long): Category?

    fun existsByTitle(title: String): Boolean
}
