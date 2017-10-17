package net.nander.botproject.mongo

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document

/**
 * Connects with MongoDB
 */
object MongolDatabaseConnector {
    val collection: MongoCollection<Document>

    init {
        val mongo = MongoClient("localhost", 27017)

        // Accessing the database
        val database = mongo.getDatabase("myDb")
        collection = database.getCollection("sampleCollection")
    }
}