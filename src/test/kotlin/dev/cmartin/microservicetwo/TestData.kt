package dev.cmartin.microservicetwo

import dev.cmartin.microservicetwo.Model.Country

object TestData {
    val spain = Country("es", "Spain")
    val france = Country("fr", "France")
    val countries: List<Country> = listOf(france, spain)
    val sortByName = CountryService.Companion.SortableProperties.NAME
    val sortByCode = CountryService.Companion.SortableProperties.CODE
}