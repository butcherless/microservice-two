package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.AirportService.Companion.SortableProperties
import dev.cmartin.microservicetwo.Model.Airport
import dev.cmartin.microservicetwo.Model.AirportNotFoundException
import jakarta.validation.constraints.Pattern
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
/**
 * x /ms-two/airports'
 * x /ms-two/airports?sortedBy=iata'
 * x /ms-two/airports?sortedBy=icao'`
 * /ms-two/airports/iata/mad'`
 * /ms-two/airports/icao/lemd'`
 * /ms-two/airports?name=Barajas'`
 */

@RestController
@RequestMapping("ms-two/airports")
class AirportController(private val airportService: AirportService) {

    @GetMapping("", "/")
    fun getAirports(
        @RequestParam(defaultValue = "name") sortedBy: String,
        @RequestParam(defaultValue = "") name: String
    ): Flux<Airport> =
        when {
            name.isNotEmpty() -> getByName(name)
            else -> getAllSorted(sortedBy)
        }

    private fun getByName(name: String): Flux<Airport> =
        this.airportService
            .findByName(name)
            .handleMissingAirportError(name)
            .also { logger.debug("$GET_BY_NAME: $name") }
            .toFlux()


    private fun getAllSorted(sortedBy: String): Flux<Airport> =
        this.airportService.findAll(resolveSortableProperty(sortedBy))
            .also { logger.debug("$GET_SORTED_BY: $sortedBy") }


    @GetMapping("/iata/{code}")
    fun getByIataCode(
        @PathVariable(required = true)
        @Pattern(regexp = "[A-Za-z]{3}", message = "Must be 3-letter code")
        code: String
    ): Flux<Airport> =
        this.airportService
            .findByIataCode(code.uppercase())
            .handleMissingAirportError(code)
            .also { logger.debug("$GET_BY_IATA_CODE: $code") }
            .toFlux()

    @GetMapping("/icao/{code}")
    fun getByIcaoCode(
        @PathVariable(required = true)
        @Pattern(regexp = "[A-Za-z]{4}", message = "Must be 4-letter code")
        code: String
    ): Flux<Airport> =
        this.airportService
            .findByIcaoCode(code.uppercase())
            .handleMissingAirportError(code)
            .also { logger.debug("$GET_BY_ICAO_CODE: $code") }
            .toFlux()

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AirportController::class.java)

        private const val GET_BY_IATA_CODE = "get airport by iata code"
        private const val GET_BY_ICAO_CODE = "get airport by iata code"
        private const val GET_BY_NAME = "get airports by name"
        private const val GET_SORTED_BY = "get airports sorted by"

        private fun Mono<Airport>.handleMissingAirportError(identifier: String) =
            this.switchIfEmpty(Mono.error(AirportNotFoundException(identifier)))

        private fun Flux<Airport>.handleMissingAirportError(identifier: String) =
            this.switchIfEmpty(Flux.error(AirportNotFoundException(identifier)))

        private fun resolveSortableProperty(sortedBy: String): SortableProperties =
            when (sortedBy.uppercase()) {
                SortableProperties.IATA.name -> SortableProperties.IATA
                SortableProperties.ICAO.name -> SortableProperties.ICAO
                else -> SortableProperties.NAME
            }
    }
}
