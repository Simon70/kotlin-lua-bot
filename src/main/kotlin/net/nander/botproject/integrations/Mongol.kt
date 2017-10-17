package net.nander.botproject.integrations

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import net.nander.botproject.mongo.MongolDatabaseConnector
import org.bson.Document
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.ThreeArgFunction
import org.luaj.vm2.lib.TwoArgFunction
/**
 * Very basic interfacing with MongoDB
 */
@Suppress("unused", "ClassName")
class Mongol : TwoArgFunction() {

    /**
     * Loads the Mongol module
     * @param moduleName which module-name to load it as
     * @param env which environment to load
     */
    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("getData", getData())
        library.set("getAllData", getAllData())
        library.set("setData", setData())
        env!!.set("mongo", library)
        return library
    }

    /**
     * Gets the data for a certain title, subTitle.
     */
    internal class getData : TwoArgFunction() {
        /**
         * Get data from a certain document, from title and subtitle.
         * @param title  The title of the requested data
         * @param subTitle The subtitle of the requested data
         * @return The requested data.
         */
        override fun call(title: LuaValue?, subTitle: LuaValue?): LuaValue {
            val documentCollection = MongolDatabaseConnector.collection.find(Filters.eq("title", title.toString()))
            if (documentCollection.first() == null)
                return LuaValue.NIL
            val data = documentCollection.first()[subTitle.toString()].toString()
            if (data == "null")
                return LuaValue.NIL
            return LuaValue.valueOf(data)
        }
    }

    /**
     * Gets all data for a certain title, regardless of subTitle. Gets back a Lua-Table with subTitle -> data structure.
     */
    internal class getAllData : OneArgFunction() {
        /**
         * Get all data from a certain document.
         * @param title The title of the set of data that' s wanted.
         * @return A Lua-Table, with as keys the subTitles, and as values the data belonging to these subTitles.
         */
        override fun call(title: LuaValue?): LuaValue {
            val documentCollection = MongolDatabaseConnector.collection.find(Filters.eq("title", title.toString()))
            val document = documentCollection.iterator().next()
            val documentIterator = document.iterator()
            var str = "{"
            var omitFirstN = 2
            var noCommaBeforeFirst = true
            while (documentIterator.hasNext()) {
                val f = documentIterator.next()
                if (omitFirstN <= 0) {
                    if (!noCommaBeforeFirst)
                        str += ","
                    str += f.key + ":" + f.value
                    noCommaBeforeFirst = false
                }
                omitFirstN--
            }
            str += "}"
            return LuaValue.valueOf(str)
        }
    }

    /**
     * Saves data, as an entry, for a certain title, subtitle
     */
    internal class setData : ThreeArgFunction() {
        /**
         * Sets the data for a document with a title and subtitle.
         * @param title The main document to be used
         * @param subTitle The sub-document to be edited
         * @param data The data to be saved
         * @return true if succesful.
         */
        override fun call(title: LuaValue?, subtitle: LuaValue?, data: LuaValue?): LuaValue {
            val documentCollection = MongolDatabaseConnector.collection.find(Filters.eq("title", title.toString()))
            if (documentCollection.first() == null)
                MongolDatabaseConnector.collection.insertOne(Document("title", title.toString()).append(subtitle.toString(), data.toString()))
            else
                MongolDatabaseConnector.collection.findOneAndUpdate(Filters.eq("title", title.toString()), Updates.set(subtitle.toString(), data.toString()))
            return LuaValue.valueOf(true)
        }
    }

}