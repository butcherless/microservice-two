package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.AirportService.Companion.SortableProperties
import dev.cmartin.microservicetwo.Model.Airport

object TestData {
    const val SPAIN_CODE = "es"
    const val MAD_IATA = "MAD"
    const val MAD_ICAO = "LEMD"
    const val TFN_IATA = "TFN"
    const val TFN_ICAO = "GXCO"

    val mad = Airport(MAD_IATA, MAD_ICAO, "Madrid Barajas", SPAIN_CODE)
    val tfn = Airport(TFN_IATA, TFN_ICAO, "Tenerife Norte", SPAIN_CODE)
    val airports: List<Airport> = listOf(mad, tfn)
    val sortByName = SortableProperties.NAME
    val sortByIataCode = SortableProperties.IATA
    val sortByIcaoCode = SortableProperties.ICAO
}

