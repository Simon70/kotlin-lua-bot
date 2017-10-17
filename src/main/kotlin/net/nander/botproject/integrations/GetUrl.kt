package net.nander.botproject.integrations

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * Gets the contents of a page for a certain URL
 */
@Suppress("unused")
class GetUrl : TwoArgFunction() {

    /**
     * Loads the getUrl module
     * @param moduleName which module-name to load it as
     * @param env which environment to load
     */
    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("GET", GET())
        env!!.set("getUrl", library)
        return library
    }

    /**
     * Gets the contents of a page for the given url
     */
    internal class GET : OneArgFunction() {
        /**
         * Handles getting the URL
         * @param url the LuaValue to use; the web-url
         * @return the contents of the requested page.
         */
        override fun call(url: LuaValue?): LuaValue {
            val url = URL(url.toString())
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