package net.nander.botproject.integrations

import net.nander.botproject.mongo.MongolDatabaseConnector
import org.bson.Document
import org.luaj.vm2.lib.TwoArgFunction

import org.luaj.vm2.LuaValue
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.ThreeArgFunction
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.InputStreamReader
import java.io.BufferedReader
import java.net.URL


class GetUrl : TwoArgFunction() {

    override fun call(modname: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("GET", GET())

        env!!.set("mongo", library)
        return library
    }

    internal class GET : OneArgFunction() {
        override fun call(x: LuaValue?): LuaValue {
            val url = URL(x.toString())
            val conn = url.openConnection()
            val reader = BufferedReader(
                    InputStreamReader(
                            conn.getInputStream()))
            val inputAsString = reader.use { it.readText() }

            reader.close()
            return LuaValue.valueOf(inputAsString)
        }
    }

}