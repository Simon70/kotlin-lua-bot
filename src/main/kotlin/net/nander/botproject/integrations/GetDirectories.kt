package net.nander.botproject.integrations

import net.nander.botproject.Bot
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction
import java.io.File

/**
 * Creates a module, which exposes the list of directories.
 */
@Suppress("unused")
class GetDirectories : TwoArgFunction() {

    /**
     * Loads the getDirectories module
     * @param moduleName which module-name to load it as
     * @param env which environment to load
     */
    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("GET", GET())
        env!!.set("getDirs", library)
        return library
    }

    /**
     * Gets the directory-list as a json
     */
    internal class GET : OneArgFunction() {
        /**
         * Handles calling this function
         * @param path the LuaValue to use; the file-path
         * @return A Lua-Table, containing a list of all directories in the asked folder.
         */
        override fun call(path: LuaValue?): LuaValue {
            val a = Bot.gson.toJson(File(path.toString()).list())
            return LuaValue.valueOf(a)
        }
    }
}