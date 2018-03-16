package at.karriere.kotlinwebluxdemo.config

import at.karriere.kotlinwebluxdemo.handlers.TweetHandler
import at.karriere.kotlinwebluxdemo.repositories.SequenceRepositoryImpl
import at.karriere.kotlinwebluxdemo.repositories.TweetRepository
import at.karriere.kotlinwebluxdemo.services.TweetServiceImpl
import at.karriere.kotlinwebluxdemo.web.Routes
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions


fun beans() = beans {
    bean<TweetRepository>()
    bean("sequenceRepository") {
        SequenceRepositoryImpl(ref())
    }
    bean("tweetService") {
        TweetServiceImpl(ref("tweetRepository"), ref("sequenceRepository"))
    }
    bean<TweetHandler>()
    bean<Routes>()
    bean("webHandler") {
        RouterFunctions.toWebHandler(ref<Routes>().router(), HandlerStrategies.builder().viewResolver(ref()).build())
    }
}