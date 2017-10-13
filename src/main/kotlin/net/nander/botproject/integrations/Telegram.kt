package net.nander.botproject.integrations

import com.google.gson.Gson
import net.nander.botproject.Bot

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.ThreeArgFunction
import org.luaj.vm2.lib.TwoArgFunction
import org.telegram.telegrambots.api.methods.send.SendMessage


/**
 * Created by nander on 13-10-17.
 */
class Telegram : TwoArgFunction() {

    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("sendMessage", sendMessage())
        library.set("sendReplyMessage", sendReplyMessage())
        env!!.set("sendMessage", library)
        return library
    }

    internal class sendMessage : TwoArgFunction() {
        override fun call(id: LuaValue?, message: LuaValue?): LuaValue {
            Bot.server!!.sendMessage(SendMessage(id!!.tolong(), message.toString()))
            return LuaValue.NIL
        }
    }

    internal class sendReplyMessage : ThreeArgFunction() {
        override fun call(id: LuaValue?, replyTo: LuaValue?, message: LuaValue?): LuaValue {
            val reply = replyTo!!.toint()
            Bot.server!!.sendMessage(SendMessage(id!!.tolong(), message.toString()).setReplyToMessageId(reply))
            return LuaValue.NIL
        }
    }

}