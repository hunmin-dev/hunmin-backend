package com.domain.aggregate

interface DomainEvent {

    fun events(): List<Any> = emptyList()

    fun isPublishStatus(): Boolean = true

    fun order(): Int = 0
}
