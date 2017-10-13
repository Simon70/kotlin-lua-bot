package net.nander.botproject.integrations

import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import java.io.InputStreamReader
import java.io.BufferedReader
import java.net.URL

@Suppress("unused")
class GetUrl : TwoArgFunction() {

    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
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