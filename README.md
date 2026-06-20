# Microservice Two

## Status

![GitHub CI](https://github.com/butcherless/microservice-two/workflows/CI/badge.svg)

Microservice Two is a reactive airport lookup service built with Spring WebFlux and Kotlin. It is also used to explore
integration with Spring Cloud Gateway.

The service reads airport data from a bundled JSON resource and supports listing, sorting, and searching airports by
name, IATA code, or ICAO code.

## Tech stack

- Spring Boot 4.1.0
- Kotlin 2.4.0
- Java 25
- Maven 3.9.16 through the Maven Wrapper
- Spring WebFlux and Project Reactor
- JaCoCo for code coverage
- Apache Maven Build Cache Extension 1.2.3

## Prerequisites

- Java 25
- Git

Maven does not need to be installed separately. Check the Java and Maven versions selected by the wrapper:

```bash
./mvnw --version
```

## Build and test

Run the complete build:

```bash
./mvnw clean verify
```

This compiles the application, runs 15 unit tests and 1 integration test, creates the executable JAR, and generates the
JaCoCo report under `target/site/jacoco/`.

## Run the service

Build and run the executable JAR:

```bash
./mvnw clean package
java -jar target/microservice-two-0.0.1-SNAPSHOT.jar
```

For development, run it directly through Spring Boot:

```bash
./mvnw spring-boot:run
```

The service listens on port `8082`.

## Useful commands

### List dependencies

```bash
./mvnw dependency:list -DincludeGroupIds=org.springframework
./mvnw dependency:list -DincludeGroupIds=org.jetbrains.kotlin
```

### Check dependency updates

```bash
./mvnw versions:display-dependency-updates
```

### Force all tests while retaining cache writes

Bypass existing cache entries, run all unit and integration tests, and save the new result:

```bash
./mvnw clean verify -Dunit.test.skip=false -Dmaven.build.cache.skipCache=true
```

### Force all tests with caching disabled

Disable both cache reads and writes:

```bash
./mvnw clean verify -Dunit.test.skip=false -Dmaven.build.cache.enabled=false
```

### Maven build cache

A normal `clean verify` restores matching build results from the local cache when possible:

```bash
./mvnw clean verify
```

Local cache entries are stored under `~/.m2/build-cache`. The project retains up to three cached builds per artifact. CI
persists both `~/.m2/repository` and `~/.m2/build-cache`.

## Airport API

Base URL:

```text
http://localhost:8082/ms-two/airports
```

The `sortedBy` parameter accepts `name`, `iata`, or `icao`. An unsupported value falls back to sorting by name. IATA
codes must contain three letters and ICAO codes must contain four letters. Invalid codes return HTTP `400`; missing
airports return HTTP `404` with a JSON error response.

### HTTPie examples

| Command                                              | Description                                |
|------------------------------------------------------|--------------------------------------------|
| `http -v ':8082/ms-two/airports'`                    | Retrieve all airports sorted by name       |
| `http -v ':8082/ms-two/airports?sortedBy=iata'`      | Retrieve all airports sorted by IATA code  |
| `http -v ':8082/ms-two/airports?sortedBy=icao'`      | Retrieve all airports sorted by ICAO code  |
| `http -v ':8082/ms-two/airports/iata/mad'`           | Retrieve an airport by its IATA code       |
| `http -v ':8082/ms-two/airports/icao/lemd'`          | Retrieve an airport by its ICAO code       |
| `http -v ':8082/ms-two/airports?name=Barajas'`       | Search airports by a partial name          |
| `http -v ':8082/ms-two/airports?name=International'` | Search for multiple matching airport names |

## Actuator endpoints

The application exposes the following actuator endpoints:

```bash
http -v ':8082/actuator/health'
http -v ':8082/actuator/loggers'
http -v ':8082/actuator/env'
http -v ':8082/actuator/mappings'
```

## Continuous integration

GitHub Actions runs the project on Java 25. The CI job:

1. Restores Maven dependencies and build-cache entries.
2. Runs `./mvnw -B -V clean verify`.
3. Uploads the generated JaCoCo coverage report to Codecov.
