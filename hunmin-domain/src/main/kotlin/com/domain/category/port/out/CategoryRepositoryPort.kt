package com.domain.category.port.out

import com.domain.aggregate.AggregateRepository
import com.domain.category.Category

interface CategoryRepositoryPort : AggregateRepository<Category, Long> {

    fun existsByTitle(title: String): Boolean
}
