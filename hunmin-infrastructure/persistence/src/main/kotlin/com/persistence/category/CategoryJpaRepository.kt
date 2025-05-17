package com.persistence.category

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<CategoryJpaEntity, Long> {
    fun existsCategoryByTitle(title: String): Boolean
}
