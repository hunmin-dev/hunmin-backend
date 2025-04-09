package com.common

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class CommonApplication

fun main(args: Array<String>) {
    runApplication<CommonApplication>(*args)
}
