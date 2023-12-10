package dev.cmartin.microservicetwo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.cmartin.microservicetwo.Model.Airport
import org.springframework.core.io.ClassPathResource

object ApplicationUtils {
    /**
     * Reads a JSON file and converts it into a list of [Model.Airport] objects.
     *
     * @param path The path to the JSON file.
     * @return A list of [Model.Airport] objects.
     */
    fun readJsonFile(path: String): List<Airport> =
        jacksonObjectMapper()
            .readValue<List<Airport>>(ClassPathResource(path).inputStream)
}