package at.karriere.kotlinwebluxdemo.services

import at.karriere.kotlinwebluxdemo.domain.Tweet
import at.karriere.kotlinwebluxdemo.repositories.SequenceRepository
import at.karriere.kotlinwebluxdemo.repositories.TweetRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TweetServiceImpl(private val tweetRepository: TweetRepository, private val sequenceRepository: SequenceRepository) : TweetService {

    private val sequenceKey = "tweets"

//    @PostConstruct
//    fun initData() {
//        arrayOf(Tweet("My First Tweet! #achievementunlocked #blessed"),
//                Tweet("O. M. G. i can't even #basicbitch #pumpkinspiced"),
//                Tweet("some inspirational blabla.. #lovelife #blessed #inspire"))
//                .forEach { save(it).block() }
//    }

    override fun findById(id: Int): Mono<Tweet> = tweetRepository.findById(id)

    override fun save(tweet: Tweet): Mono<Tweet> {
        if (tweet.id == -1)
            tweet.id = sequenceRepository.getNextSequenceId(sequenceKey)

        return tweetRepository.save(tweet)
    }

    override fun delete(id: Int): Mono<Void> = tweetRepository.deleteById(id)

    override fun findAll(): Flux<Tweet> = tweetRepository.findAll()
}