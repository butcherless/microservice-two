# Microservice Two

## Status

![Github CI](https://github.com/butcherless/microservice-two/workflows/CI/badge.svg)

## Proof of concept and research with colleagues and friends.

Goals:

- Airport Service behind a _Gateway_
- Tinker with _Spring Cloud Gateway_

Tech stack:

- Spring Boot 3.2.x
- Kotlin 2.0.x
- Java 21

## New project basic structure

Check _Java_ and _Maven_ versions:

    ./mvnw -v

```bash
mkdir my-multi-module-project
mvn archetype:generate -DgroupId=dev.cmartin -DartifactId=learn-spring-cloud -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
mvn archetype:generate -DgroupId=dev.cmartin -DartifactId=microservice-two -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
mkdir -p src/{main,test}/{java,resources} src/main/java/dev/cmartin/learn
```

## Useful commands

Run the microservice:

    java -jar target/microservice-two-0.0.1-SNAPSHOT.jar

Dependency list

    ./mvnw dependency:list -DincludeGroupIds=org.springframework
    ./mvnw dependency:list -DincludeGroupIds=org.jetbrains.kotlin

Dependency updates

    ./mvnw versions:display-dependency-updates

## HTTP client commands [`httpie`]

| Command                                              | Description                                               |
|------------------------------------------------------|-----------------------------------------------------------|
| `http -v ':8082/ms-two/airports'`                    | Retrieve all airports sorted by name (default limit)      |
| `http -v ':8082/ms-two/airports?sortedBy=iata'`      | Retrieve all airports sorted by iata code (default limit) |
| `http -v ':8082/ms-two/airports?sortedBy=icao'`      | Retrieve all airports sorted by icao code (default limit) |
| `http -v ':8082/ms-two/airports/iata/mad'`           | Retrieve an airport by its IATA code                      |
| `http -v ':8082/ms-two/airports/icao/lemd'`          | Retrieve an airport by its ICAO code                      |
| `http -v ':8082/ms-two/airports?name=Barajas'`       | Retrieve all airports containing the name case single     |
| `http -v ':8082/ms-two/airports?name=International'` | Retrieve all airports containing the name case multiple   |