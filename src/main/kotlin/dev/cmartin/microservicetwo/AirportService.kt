package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.Model.Airport
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AirportService {
    /**
     * Finds all airports based on the provided sorting property and limit.
     *
     * @param sortByProperty The property to sort the airports by. Acceptable values are "IATA", "ICAO", and "NAME".
     * @param limit The maximum number of airports to return. Default value is 20.
     * @return A Flux containing the list of airports.
     */
    fun findAll(sortByProperty: SortableProperties, limit: Int = 20): Flux<Airport>

    /**
     * Finds an airport by its IATA code.
     *
     * @param code The IATA code of the airport.
     * @return A Mono emitting the airport found.
     */
    fun findByIataCode(code: String): Mono<Airport>

    /**
     * Finds an airport by its ICAO code.
     *
     * @param code The ICAO code of the airport.
     * @return A Mono emitting the airport found.
     */
    fun findByIcaoCode(code: String): Mono<Airport>

    /**
     * Finds airports by name.
     *
     * @param name The name of the airport.
     * @return A Flux emitting the list of airports matching the name.
     */
    fun findByName(name: String): Flux<Airport>

    companion object {
        enum class SortableProperties {
            IATA,
            ICAO,
            NAME
        }
    }
}