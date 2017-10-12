package net.nander.botproject.mongo

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document

/**
 * Created by nander on 12-10-17.
 */
object MongolDatabaseConnector {
    val collection: MongoCollection<Document>
    init {
        val mongo = MongoClient("localhost", 27017)
        println("Connected to the database successfully")

        // Accessing the database
        val database = mongo.getDatabase("myDb")
        collection = database.getCollection("sampleCollection")
    }
}