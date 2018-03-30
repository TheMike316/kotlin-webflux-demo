package at.karriere.kotlinwebluxdemo.repositories

import at.karriere.kotlinwebluxdemo.domain.SequenceId
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class SequenceRepositoryImpl(val mongoOperations: MongoOperations) : SequenceRepository {

    override fun getNextSequenceId(key: String): Int {
        //get sequence id
        val query = Query(Criteria.where("_id").`is`(key))

        //increase sequence id by 1
        val update = Update()
        update.inc("sequence", 1)

        //return new increased id
        val options = FindAndModifyOptions()
        options.returnNew(true)

        //this is the magic happened.
        val seqId = mongoOperations.findAndModify(query, update, options, SequenceId::class.java)
                ?: throw RuntimeException("Unable to get sequence id for key : $key")

        //if no id, throws Exception
        //optional, just a way to tell user when the sequence id is failed to generate.

        return seqId.sequence
    }

    override fun saveSequence(sequenceId: SequenceId) {
        mongoOperations.save(sequenceId)
    }

    @PostConstruct
    private fun init() {
        saveSequence(SequenceId(0, "tweets"))
    }
}