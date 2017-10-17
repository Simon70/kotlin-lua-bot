package net.nander.botproject.integrations

import net.nander.botproject.Bot
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction
import java.io.File

@Suppress("unused")
class GetDirectories : TwoArgFunction() {

    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("GET", GET())
        env!!.set("getDirs", library)
        return library
    }

    internal class GET : OneArgFunction() {
        override fun call(x: LuaValue?): LuaValue {
            val a = Bot.gson.toJson(File("scripts/" + x.toString()).list())
            return LuaValue.valueOf(a)
        }
    }
}