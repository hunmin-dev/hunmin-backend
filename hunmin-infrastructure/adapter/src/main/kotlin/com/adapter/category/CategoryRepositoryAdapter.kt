package com.adapter.category

import com.domain.category.Category
import com.domain.category.port.out.CategoryRepositoryPort
import com.persistence.category.CategoryJpaRepository
import com.persistence.category.CategoryPersistenceMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class CategoryRepositoryAdapter(
    private val categoryJpaRepository: CategoryJpaRepository,
    private val categoryPersistenceMapper: CategoryPersistenceMapper,
) : CategoryRepositoryPort {

    override fun save(aggregate: Category): Category {
        val categoryEntity = categoryPersistenceMapper.toEntity(aggregate)
        val savedCategory = categoryJpaRepository.save(categoryEntity)
        return categoryPersistenceMapper.toDomain(savedCategory)
    }

    override fun findByIdOrNull(id: Long): Category? =
        categoryJpaRepository.findByIdOrNull(id)
            ?.let { categoryPersistenceMapper.toDomain(it) }

    override fun existsByTitle(title: String): Boolean =
        categoryJpaRepository.existsCategoryByTitle(title)

    override fun existsById(id: Long): Boolean =
        categoryJpaRepository.existsById(id)
}
