package at.karriere.kotlinwebluxdemo.repositories

import at.karriere.kotlinwebluxdemo.domain.Tweet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TweetRepository : ReactiveMongoRepository<Tweet, Int>