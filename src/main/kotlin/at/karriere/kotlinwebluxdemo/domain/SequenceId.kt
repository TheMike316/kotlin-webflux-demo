package at.karriere.kotlinwebluxdemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sequence")
data class SequenceId(var sequence: Int, @field: Id var id: String)
