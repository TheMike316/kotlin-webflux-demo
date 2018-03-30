package at.karriere.kotlinwebluxdemo

import at.karriere.kotlinwebluxdemo.domain.Tweet
import at.karriere.kotlinwebluxdemo.services.TweetService
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
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
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.createdAt").isEqualTo(tweet.createdAt)
                .jsonPath("$.text").isEqualTo(tweet.text)

    }

    @Test
    fun testGetSingleTweet() {
        val tweet = tweetService.save(Tweet("Hello, World!")).block()

        webTestClient.get()
                .uri("/api/tweets/{id}", Collections.singletonMap("id", tweet?.id))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { response -> Assertions.assertThat(response.responseBody).isNotNull() }
    }

    @Test
    fun testUpdateTweet() {
        val tweet = tweetService.save(Tweet("Initial Tweet")).block()

        val newTweetData = Tweet("Updated Tweet")
        newTweetData.id = tweet!!.id

        webTestClient.put()
                .uri("/api/tweets/${tweet.id}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just<Tweet>(newTweetData), Tweet::class.java)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.text").isEqualTo("Updated Tweet")
                .jsonPath("$.id").isEqualTo(tweet.id)
    }

    @Test
    fun testDeleteTweet() {
        val tweet = tweetService.save(Tweet("To be deleted")).block()

        webTestClient.delete()
                .uri("/api/tweets/{id}", Collections.singletonMap("id", tweet?.id))
                .exchange()
                .expectStatus().isOk
    }
}
