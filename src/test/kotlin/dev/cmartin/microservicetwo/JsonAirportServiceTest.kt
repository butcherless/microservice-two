package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.Model.Airport
import dev.cmartin.microservicetwo.TestData.airports
import dev.cmartin.microservicetwo.TestData.mad
import dev.cmartin.microservicetwo.TestData.sortByIataCode
import dev.cmartin.microservicetwo.TestData.sortByIcaoCode
import dev.cmartin.microservicetwo.TestData.sortByName
import dev.cmartin.microservicetwo.TestData.tfn
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class JsonAirportServiceTest {
    private val countryMap: ConcurrentMap<String, Airport> =
        ConcurrentHashMap(
            airports.associateBy { it.iataCode }
        )

    private val service: AirportService = JsonAirportService(countryMap)

    @Test
    fun `should find all airports sorted by iata code`() {
        StepVerifier
            .create(service.findAll(sortByIataCode))
            .expectNext(mad)
            .expectNext(tfn)
            .verifyComplete()
    }

    @Test
    fun `should find all airports sorted by icao code`() {
        StepVerifier
            .create(service.findAll(sortByIcaoCode))
            .expectNext(tfn)
            .expectNext(mad)
            .verifyComplete()
    }


    @Test
    fun `should find all airports sorted by name`() {
        StepVerifier
            .create(service.findAll(sortByName))
            .expectNext(mad)
            .expectNext(tfn)
            .verifyComplete()
    }

    @Test
    fun `should find airport by iata code`() {
        StepVerifier
            .create(service.findByIataCode(mad.iataCode))
            .expectNext(mad)
            .verifyComplete()
    }

    @Test
    fun `should find airport by icao code`() {
        StepVerifier
            .create(service.findByIcaoCode(mad.icaoCode))
            .expectNext(mad)
            .verifyComplete()
    }

    @Test
    fun `should find airport by name`() {
        StepVerifier
            .create(service.findByName(mad.name))
            .expectNext(mad)
            .verifyComplete()
    }

    @Test
    fun `should retrieve an empty stream for missing airport`() {
        StepVerifier
            .create(service.findByName("missing"))
            .verifyComplete()
    }
}

