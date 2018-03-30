package at.karriere.kotlinwebluxdemo.config

import at.karriere.kotlinwebluxdemo.handlers.TweetHandler
import at.karriere.kotlinwebluxdemo.web.router
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouterConfig {

    @Bean
    fun routerFunction(tweetHandler: TweetHandler) = router(tweetHandler)
}