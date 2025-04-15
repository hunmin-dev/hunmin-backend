package com.api.helper

import io.restassured.RestAssured
import jakarta.persistence.EntityManager
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * 통합테스트 테스트 격리용 어노테이션
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DatabaseCleanupListener::class
    ]
)
annotation class IntegrationTest

/**
 * IntegrationTest 어노테이션에서 데이터베이스 초기화를 처리하는 리스너
 */
class DatabaseCleanupListener : AbstractTestExecutionListener() {

    override fun beforeTestExecution(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val transactionManager = applicationContext.getBean(PlatformTransactionManager::class.java)
        val entityManager = applicationContext.getBean(EntityManager::class.java)

        val restAssuredPortForTest = applicationContext.environment.getProperty("local.server.port")
            ?.toInt()
            ?: 0

        RestAssured.port = restAssuredPortForTest

        TransactionTemplate(transactionManager).executeWithoutResult {
            val tables = entityManager.createNativeQuery(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'"
            ).resultList

            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
            tables.forEach { tableName ->
                entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            }
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
        }
    }
}
