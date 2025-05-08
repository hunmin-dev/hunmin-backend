package com.application.interceptor

import com.domain.aggregate.AggregateRoot
import mu.KotlinLogging
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.context.ApplicationEventPublisher

class DomainEventPublisherInterceptor(
    private val eventPublisher: ApplicationEventPublisher,
    private var isDisabled: Boolean = false
) : MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {
        log.debug { "Intercepting method: ${invocation.method.name}" }

        return try {
            val aggregate = invocation.arguments.first()
                ?: throw IllegalArgumentException("Aggregate argument is null")
            val result = invocation.proceed()
            publishDomainEvents(aggregate)
            result
        } catch (exception: Throwable) {
            log.error(exception) { "Failed to invoke method: ${invocation.method.name}, error: ${exception.message}" }
            throw exception
        }
    }

    private fun publishDomainEvents(aggregate: Any) {
        if (aggregate !is AggregateRoot<*, *>) {
            log.debug { "Aggregate is not AggregateRoot: ${aggregate.javaClass.simpleName}" }
            return
        }

        val events = aggregate.events()
        if (events.isEmpty()) {
            log.debug { "Aggregate event is empty to publish: ${aggregate.javaClass.simpleName}" }
            return
        }

        events.filter { it.isPublishStatus() }
            .sortedBy { it.order() }
            .forEach { domainEvent ->
                if (!isDisabled) {
                    log.info { "Publishing event: ${domainEvent.javaClass.simpleName}, ${domainEvent.events()}" }
                    eventPublisher.publishEvent(domainEvent)
                } else {
                    log.debug { "Event publishing disabled, skipping event: ${domainEvent.javaClass.simpleName}" }
                }
            }

        aggregate.clear()
        log.debug { "Cleared events for aggregate: ${aggregate.javaClass.simpleName}" }
    }

    // 테스트용 설정자
    fun setDisabled(disabled: Boolean) {
        isDisabled = disabled
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
