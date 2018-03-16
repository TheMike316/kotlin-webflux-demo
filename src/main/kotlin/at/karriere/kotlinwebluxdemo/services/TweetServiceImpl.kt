package at.karriere.kotlinwebluxdemo.services

import at.karriere.kotlinwebluxdemo.domain.Tweet
import at.karriere.kotlinwebluxdemo.repositories.SequenceRepository
import at.karriere.kotlinwebluxdemo.repositories.TweetRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class TweetServiceImpl(private val tweetRepository: TweetRepository, private val sequenceRepository: SequenceRepository) : TweetService {

    private val sequenceKey = "tweets"

    override fun findById(id: Int): Mono<Tweet> = tweetRepository.findById(id)

    override fun save(tweet: Tweet): Mono<Tweet> {
        if (tweet.id == -1)
            tweet.id = sequenceRepository.getNextSequenceId(sequenceKey)

        return tweetRepository.save(tweet)
    }

    override fun delete(id: Int): Mono<Void> = tweetRepository.deleteById(id)

    override fun findAll(): Flux<Tweet> = tweetRepository.findAll()
}