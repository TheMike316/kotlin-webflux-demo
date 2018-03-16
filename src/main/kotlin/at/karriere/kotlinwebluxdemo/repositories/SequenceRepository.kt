package at.karriere.kotlinwebluxdemo.repositories

import at.karriere.kotlinwebluxdemo.domain.SequenceId

interface SequenceRepository {

    fun getNextSequenceId(key: String): Int

    fun saveSequence(sequenceId: SequenceId)
}