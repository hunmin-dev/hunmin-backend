package com.persistence.category

import com.domain.category.Category
import org.springframework.stereotype.Component

@Component
class CategoryPersistenceMapper {
    fun toEntity(category: Category): CategoryJpaEntity =
        CategoryJpaEntity(
            id = category.id,
            title = category.title,
            isVisible = category.isVisible,
        )

    fun toDomain(entity: CategoryJpaEntity): Category =
        Category(
            id = entity.id,
            title = entity.title,
            isVisible = entity.isVisible,
            createdAt = entity.createdAt.toEpochMilli(),
        )
}
