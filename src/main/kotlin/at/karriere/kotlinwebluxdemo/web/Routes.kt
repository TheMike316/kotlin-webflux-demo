package at.karriere.kotlinwebluxdemo.web

import at.karriere.kotlinwebluxdemo.handlers.TweetHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


fun router(tweetHandler: TweetHandler) = router {
    accept(MediaType.ALL).nest {
        "".nest {
            GET("/", { _ -> ServerResponse.ok().body(Mono.just("Hello there")) })
        }
    }
    accept(MediaType.APPLICATION_JSON).nest {
        "/api".nest {
            "/tweets".nest {
                GET("", tweetHandler::findAll)
                POST("", tweetHandler::save)
                GET("/{id}", tweetHandler::findById)
                PUT("/{id}", tweetHandler::update)
                DELETE("/{id}", tweetHandler::delete)
            }
        }
    }
    accept(MediaType.TEXT_EVENT_STREAM).nest {
        GET("/stream/tweets", tweetHandler::findAll)
    }
}
