package com.application.config

import com.application.interceptor.DomainEventPublisherInterceptor
import mu.KotlinLogging
import org.springframework.aop.Advisor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = ["com.application.interceptor", "com.application.config"])
class ApplicationConfig {

    @Bean
    fun domainEventAdvisor(
        applicationEventPublisher: ApplicationEventPublisher,
        @Value("\${domain.event.isDisable:false}") isDisabled: Boolean
    ): Advisor {
        val pointcut = createPointcut()
        val interceptor = DomainEventPublisherInterceptor(applicationEventPublisher, isDisabled)
        val advisor = DefaultPointcutAdvisor(pointcut, interceptor)
            .apply { order = Int.MAX_VALUE }

        log.info { "Created DomainEventAdvisor with pointcut: $DOMAIN_EVENT_POINTCUT, isDisabled: $isDisabled" }
        return advisor
    }

    private fun createPointcut(): AspectJExpressionPointcut {
        return AspectJExpressionPointcut().apply {
            expression = DOMAIN_EVENT_POINTCUT
        }
    }

    companion object {
        private const val DOMAIN_EVENT_POINTCUT =
            "execution(* com.domain..*RepositoryPort+.save(..)) || execution(* com.adapter..*.save(..))"

        private val log = KotlinLogging.logger {}
    }
}
