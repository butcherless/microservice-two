package dev.cmartin.microservicetwo

import java.time.Instant

object Model {


    data class Airport(
        val iataCode: String,
        val icaoCode: String,
        val name: String,
        val countryCode: String
    )

    /**
     * AirportNotFoundException is an exception that is thrown when an airport is not found.
     *
     * @param message the error message describing the reason for the exception.
     */
    data class AirportNotFoundException(override val message: String) : RuntimeException(message)

    /**
     * Represents an error response containing a message and an instant.
     * @property message The error message.
     * @property instant The instant when the error occurred.
     */
    data class ErrorResponse(
        val message: String,
        val instant: Instant
    )
}