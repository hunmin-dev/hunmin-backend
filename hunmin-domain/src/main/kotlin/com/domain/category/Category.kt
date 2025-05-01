package com.domain.category

class Category(
    val id: Long = 0,
    var title: String,
    var isVisible: Boolean = true,
    var createdAt: Long = 0,
    var createdBy: Long = 0,
    var updatedBy: Long = 0,
) {
    fun update(title: String? = null, isVisible: Boolean? = null) {
        this.title = title ?: this.title
        this.isVisible = isVisible ?: this.isVisible
    }
}
