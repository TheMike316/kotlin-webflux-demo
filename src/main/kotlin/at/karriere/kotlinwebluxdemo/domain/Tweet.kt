package at.karriere.kotlinwebluxdemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

@Document(collection = "tweets")
data class Tweet(
        @field: [Max(140) NotBlank] var text: String = "",
        var createdAt: Date = Date(),
        @field: Id var id: Int = -1
)
