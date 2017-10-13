package net.nander.botproject.integrations

import net.nander.botproject.mongo.MongolDatabaseConnector
import org.bson.Document
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.LuaValue
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.luaj.vm2.lib.ThreeArgFunction

@Suppress("unused")
class Mongol : TwoArgFunction() {

    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("getData", getData())
        library.set("setData", setData())

        env!!.set("mongo", library)
        return library
    }

    internal class getData : TwoArgFunction() {
        override fun call(x: LuaValue?, file: LuaValue?): LuaValue {
            val Doc = MongolDatabaseConnector.collection.find(Filters.eq("title", x.toString()))
            if (Doc.first() == null)
                return LuaValue.NIL
            val a =Doc.first()[file.toString()].toString()
            if(a == "null")
                return LuaValue.NIL
            return LuaValue.valueOf(a)
        }
    }

    internal class setData : ThreeArgFunction() {
        override fun call(x: LuaValue?,file: LuaValue?, y: LuaValue?): LuaValue {
            val Doc = MongolDatabaseConnector.collection.find(Filters.eq("title", x.toString()))
            if (Doc.first() == null)
                MongolDatabaseConnector.collection.insertOne(Document("title", x.toString()).append(file.toString(), y.toString()))
            else
                MongolDatabaseConnector.collection.findOneAndUpdate(Filters.eq("title", x.toString()), Updates.set(file.toString(), y.toString()))
            return LuaValue.valueOf(0)
        }
    }

}