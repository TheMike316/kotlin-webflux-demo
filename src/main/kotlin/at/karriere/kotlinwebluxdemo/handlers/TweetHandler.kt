package at.karriere.kotlinwebluxdemo.handlers

import at.karriere.kotlinwebluxdemo.domain.Tweet
import at.karriere.kotlinwebluxdemo.services.TweetService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
@Suppress("UNUSED_PARAMETER")
class TweetHandler(private val tweetService: TweetService) {

    fun findAll(req: ServerRequest) = ok().body(tweetService.findAll())

    fun save(req: ServerRequest) = req.bodyToMono(Tweet::class.java)
            .flatMap { tweet ->
                ServerResponse.status(HttpStatus.CREATED).body(tweetService.save(tweet))
            }

    fun update(req: ServerRequest) = req.bodyToMono(Tweet::class.java)
            .flatMap { tweet -> ok().body(tweetService.save(tweet)) }


    fun findById(req: ServerRequest) = ok().body(tweetService.findById(req.pathVariable("id").toInt()))

    fun delete(req: ServerRequest) = tweetService.delete(req.pathVariable("id").toInt())
            .flatMap { ok().body(Mono.just(Unit)) }
}