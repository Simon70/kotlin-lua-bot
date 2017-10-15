package net.nander.botproject.integrations

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import net.nander.botproject.mongo.MongolDatabaseConnector
import org.bson.Document
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.ThreeArgFunction
import org.luaj.vm2.lib.TwoArgFunction

@Suppress("unused")
class Mongol : TwoArgFunction() {

    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("getData", getData())
        library.set("getAllData", getAllData())
        library.set("setData", setData())
        env!!.set("mongo", library)
        return library
    }

    internal class getData : TwoArgFunction() {
        override fun call(x: LuaValue?, file: LuaValue?): LuaValue {
            val documentCollection = MongolDatabaseConnector.collection.find(Filters.eq("title", x.toString()))
            if (documentCollection.first() == null)
                return LuaValue.NIL
            val data = documentCollection.first()[file.toString()].toString()
            if (data == "null")
                return LuaValue.NIL
            return LuaValue.valueOf(data)
        }
    }

    internal class getAllData : OneArgFunction() {
        override fun call(x: LuaValue?): LuaValue {
            val documentCollection = MongolDatabaseConnector.collection.find(Filters.eq("title", x.toString()))
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

    internal class setData : ThreeArgFunction() {
        override fun call(x: LuaValue?, file: LuaValue?, y: LuaValue?): LuaValue {
            val documentCollection = MongolDatabaseConnector.collection.find(Filters.eq("title", x.toString()))
            if (documentCollection.first() == null)
                MongolDatabaseConnector.collection.insertOne(Document("title", x.toString()).append(file.toString(), y.toString()))
            else
                MongolDatabaseConnector.collection.findOneAndUpdate(Filters.eq("title", x.toString()), Updates.set(file.toString(), y.toString()))
            return LuaValue.valueOf(0)
        }
    }

}