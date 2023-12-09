package dev.cmartin.microservicetwo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicroserviceTwoApplication

/**
 * Application entry point
 */
fun main(args: Array<String>) {
    runApplication<MicroserviceTwoApplication>(*args)
}
