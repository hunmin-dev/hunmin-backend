package com.adapter.category

import com.domain.category.Category
import com.domain.category.port.out.CategoryRepositoryPort
import com.persistence.category.CategoryJpaRepository
import com.persistence.category.CategoryPersistenceMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class CategoryRepositoryAdapter(
    private val categoryJpaRepository: CategoryJpaRepository,
    private val categoryPersistenceMapper: CategoryPersistenceMapper,
) : CategoryRepositoryPort {
    override fun save(category: Category): Category {
        val categoryEntity = categoryPersistenceMapper.toEntity(category)
        val savedCategory = categoryJpaRepository.save(categoryEntity)
        return categoryPersistenceMapper.toDomain(savedCategory)
    }

    override fun findByCategoryId(categoryId: Long): Category? {
        return categoryJpaRepository.findById(categoryId)
            .map { categoryPersistenceMapper.toDomain(it) }
            .orElse(null)
    }

    override fun existsByTitle(title: String): Boolean = categoryJpaRepository.existsCategoryByTitle(title)
}
