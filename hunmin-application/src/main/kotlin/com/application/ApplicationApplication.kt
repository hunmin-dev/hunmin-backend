package com.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApplicationApplication

fun main(args: Array<String>) {
    runApplication<ApplicationApplication>(*args)
}
