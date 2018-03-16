package at.karriere.kotlinwebluxdemo

import at.karriere.kotlinwebluxdemo.domain.Tweet
import at.karriere.kotlinwebluxdemo.services.TweetService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class KotlinWebfluxDemoApplicationTests {

    @Autowired
    private lateinit var tweetService: TweetService

    @Autowired
    private lateinit var webTestClient: WebTestClient


    @Test
    fun testFindAll() {
        arrayOf(Tweet("asdfasdf"), Tweet("basdfasfe"), Tweet("ffdfaf")).forEach { tweetService.save(it) }

        webTestClient.get().uri("/api/tweets")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Tweet::class.java)
    }

    @Test
    fun testSaveTweet() {
        val tweet = Tweet("jasfjfkjasfsa")

        webTestClient.post().uri("/api/tweets")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(tweet), Tweet::class.java)
                .exchange()
                .expectStatus().isCreated
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .consumeWith(::println)
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.text").isEqualTo(tweet.text)
    }
}
