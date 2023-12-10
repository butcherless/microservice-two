package dev.cmartin.microservicetwo.integration

import dev.cmartin.microservicetwo.ApplicationUtils
import dev.cmartin.microservicetwo.Model.Airport
import dev.cmartin.microservicetwo.TestData.mad
import dev.cmartin.microservicetwo.TestData.tfn
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReadJsonFileTestIT {

    private val path = "test-airports.json"
    private fun readJsonFile(): Map<String, Airport> {
        val airports: List<Airport> =
            ApplicationUtils.readJsonFile(path)

        return airports.associateBy { it.iataCode }
    }

    @Test
    fun `read all airports`() {

        val airportMap = mapOf(
            mad.iataCode to mad,
            tfn.iataCode to tfn
        )
        val result = readJsonFile()

        assertEquals(airportMap, result)
    }
}