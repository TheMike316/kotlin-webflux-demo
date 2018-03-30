package at.karriere.kotlinwebluxdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoRepositories
class KotlinWebfluxDemoApplication

fun main(args: Array<String>) {
    runApplication<KotlinWebfluxDemoApplication>(*args)
}
