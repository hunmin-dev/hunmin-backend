package com.domain.aggregate

interface AggregateRepository<T : AggregateRoot<*, ID>, ID> {

    fun save(aggregate: T): T

    fun findByIdOrNull(id: ID): T?
}

