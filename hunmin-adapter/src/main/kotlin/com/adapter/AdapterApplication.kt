package com.adapter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class AdapterApplication

fun main(args: Array<String>) {
    runApplication<AdapterApplication>(*args)
}
