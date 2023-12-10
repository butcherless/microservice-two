package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.Model.Airport
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Configuration
@ConfigurationProperties(prefix = "service")
class ServiceConfiguration {
    val logger: Logger = LoggerFactory.getLogger(ServiceConfiguration::class.java)

    @Value("\${service.countries.file}")
    private lateinit var airportsFile: String

    @Bean
    fun airportMap(): ConcurrentMap<String, Airport> {
        this.logger.debug("reading airports from file: $airportsFile")

        return ConcurrentHashMap(
            ApplicationUtils
                .readJsonFile(airportsFile)
                .associateBy { it.iataCode }
        )
    }
}