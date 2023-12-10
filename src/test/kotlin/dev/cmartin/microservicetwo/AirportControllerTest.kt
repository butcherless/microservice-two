package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.Model.Airport
import dev.cmartin.microservicetwo.Model.ErrorResponse
import dev.cmartin.microservicetwo.TestData.airports
import dev.cmartin.microservicetwo.TestData.mad
import dev.cmartin.microservicetwo.TestData.sortByIataCode
import dev.cmartin.microservicetwo.TestData.sortByIcaoCode
import dev.cmartin.microservicetwo.TestData.sortByName
import dev.cmartin.microservicetwo.TestData.tfn
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class AirportControllerTest {

    private val airportService = Mockito.mock(AirportService::class.java)

    private val webClient =
        WebTestClient
            .bindToController(AirportController(airportService))
            .controllerAdvice(GlobalExceptionHandler::class.java)
            .build()

    @Test
    fun `should get all airports`() {
        // given
        val expectedCountries = Flux.fromIterable(airports.sortedBy { it.name })
        Mockito
            .`when`(airportService.findAll(sortByName))
            .thenReturn(expectedCountries)

        // when
        val response: EntityExchangeResult<List<Airport>> =
            webClient
                .get()
                .uri(BASE_PATH)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Airport::class.java)
                .returnResult()

        // then
        val responseCountries: List<Airport> = response.responseBody ?: emptyList()
        assertEquals(responseCountries.size, airports.size)
        assertEquals(mad, responseCountries.first())
        assertEquals(tfn, responseCountries.last())
    }

    @Test
    fun `should get all airports sorted by iata code`() {
        // given
        val expectedCountries = Flux.fromIterable(airports.sortedBy { it.iataCode })
        Mockito
            .`when`(airportService.findAll(sortByIataCode))
            .thenReturn(expectedCountries)

        // when
        val response: EntityExchangeResult<List<Airport>> =
            webClient
                .get()
                .uri("$BASE_PATH?sortedBy=iata")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Airport::class.java)
                .returnResult()

        // then
        val responseCountries: List<Airport> = response.responseBody ?: emptyList()
        assertEquals(responseCountries.size, airports.size)
        assertEquals(mad, responseCountries.first())
        assertEquals(tfn, responseCountries.last())
    }

    @Test
    fun `should get all airports sorted by icao code`() {
        // given
        val expectedCountries = Flux.fromIterable(airports.sortedBy { it.icaoCode })
        Mockito
            .`when`(airportService.findAll(sortByIcaoCode))
            .thenReturn(expectedCountries)

        // when
        val response: EntityExchangeResult<List<Airport>> =
            webClient
                .get()
                .uri("$BASE_PATH?sortedBy=icao")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Airport::class.java)
                .returnResult()

        // then
        val responseCountries: List<Airport> = response.responseBody ?: emptyList()
        assertEquals(responseCountries.size, airports.size)
        assertEquals(tfn, responseCountries.first())
        assertEquals(mad, responseCountries.last())
    }


    @Test
    fun `should get airports by name`() {
        // given
        Mockito
            .`when`(airportService.findByName(mad.name))
            .thenReturn(Flux.just(mad))

        // when
        webClient
            .get()
            .uri("$BASE_PATH?name=${mad.name}")
            .exchange()
            // then
            .expectStatus().isOk
            .expectBodyList(Airport::class.java)
            .hasSize(1)
            .contains(mad)
    }

    @Test
    fun `should get airport by iata code`() {
        // given
        Mockito
            .`when`(airportService.findByIataCode(mad.iataCode))
            .thenReturn(Mono.just(mad))

        // when
        webClient
            .get()
            .uri("$BASE_PATH/iata/${mad.iataCode}")
            .exchange()
            // then
            .expectStatus().isOk
            .expectBodyList(Airport::class.java)
            .hasSize(1)
            .contains(mad)
    }

    @Test
    fun `should get airport by icao code`() {
        // given
        Mockito
            .`when`(airportService.findByIcaoCode(mad.icaoCode))
            .thenReturn(Mono.just(mad))

        // when
        webClient
            .get()
            .uri("$BASE_PATH/icao/${mad.icaoCode}")
            .exchange()
            // then
            .expectStatus().isOk
            .expectBodyList(Airport::class.java)
            .hasSize(1)
            .contains(mad)
    }


    @Test
    fun `should return not found for missing airport by iata code`() {
        // given
        Mockito
            .`when`(airportService.findByIataCode(MISSING_CODE.uppercase()))
            .thenReturn(Mono.empty())

        // when
        val errorResponse = webClient.get()
            .uri("$BASE_PATH/iata/$MISSING_CODE")
            .exchange()
            .expectStatus().isNotFound
            .expectBody(ErrorResponse::class.java)
            .returnResult()
            .responseBody!!

        // then
        assertEquals(MISSING_CODE, errorResponse.message)
    }

    companion object {
        const val BASE_PATH = "/ms-two/airports"
        const val MISSING_CODE = "XYZ"
    }

}
