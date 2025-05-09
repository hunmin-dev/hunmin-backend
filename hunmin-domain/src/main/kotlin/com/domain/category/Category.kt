package com.domain.category

import com.domain.aggregate.AggregateRoot
import com.domain.category.event.CategoryEvent

class Category(
    override val id: Long = 0,
    var title: String,
    var isVisible: Boolean = true,
    var createdAt: Long = 0,
) : AggregateRoot<CategoryEvent, Long>() {

    fun update(title: String? = null, isVisible: Boolean? = null) {
        this.title = title ?: this.title
        this.isVisible = isVisible ?: this.isVisible
    }
}
