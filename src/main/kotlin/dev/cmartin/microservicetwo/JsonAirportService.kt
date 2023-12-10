package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.AirportService.Companion.SortableProperties
import dev.cmartin.microservicetwo.Model.Airport
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ConcurrentMap

@Service
class JsonAirportService(private val airportMap: ConcurrentMap<String, Airport>) : AirportService {

    override fun findAll(sortByProperty: SortableProperties, limit: Int): Flux<Airport> {
        val airports = Flux.fromIterable(
            airportMap
                .values
                .take(limit)
        ).also { logger.debug("retrieving all airports. size: ${airportMap.size}") }

        return when (sortByProperty) {
            SortableProperties.IATA -> airports.sort(iataCodeComparator)
            SortableProperties.ICAO -> airports.sort(icaoCodeComparator)
            SortableProperties.NAME -> airports.sort(nameComparator)
        }
    }

    override fun findByIataCode(code: String): Mono<Airport> =
        this.airportMap[code]
            .toMono()

    override fun findByIcaoCode(code: String): Mono<Airport> =
        this.airportMap
            .values
            .find { it.icaoCode == code }
            .toMono()


    override fun findByName(name: String): Flux<Airport> =
        Flux.fromIterable(
            this.airportMap
                .values
                .filter { it.name.contains(name, ignoreCase = true) }
                .sortedBy { it.name }
        )


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JsonAirportService::class.java)

        private val iataCodeComparator = compareBy(Airport::iataCode)
        private val icaoCodeComparator = compareBy(Airport::icaoCode)
        private val nameComparator = compareBy(Airport::name)
    }
}
