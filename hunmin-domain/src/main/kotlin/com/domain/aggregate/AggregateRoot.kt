package com.domain.aggregate

abstract class AggregateRoot<T : DomainEvent, ID> {

    abstract val id: ID

    private val events: MutableList<T> = mutableListOf()

    internal fun addEvent(event: T) {
        events.add(event)
    }

    fun events(): List<T> {
        return events.toList()
    }

    fun clear() {
        events.clear()
    }
}
