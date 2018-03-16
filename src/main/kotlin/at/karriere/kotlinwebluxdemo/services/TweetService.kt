package at.karriere.kotlinwebluxdemo.services

import at.karriere.kotlinwebluxdemo.domain.Tweet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TweetService {

    fun findAll(): Flux<Tweet>

    fun findById(id: Int): Mono<Tweet>

    fun save(tweet: Tweet): Mono<Tweet>

    fun delete(id: Int): Mono<Void>

}