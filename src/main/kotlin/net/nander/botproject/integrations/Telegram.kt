package net.nander.botproject.integrations

import com.google.gson.Gson
import net.nander.botproject.Bot
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.ThreeArgFunction
import org.luaj.vm2.lib.TwoArgFunction
import org.telegram.telegrambots.api.methods.send.SendMessage
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by nander on 13-10-17.
 */
class Telegram : TwoArgFunction() {

    override fun call(moduleName: LuaValue, env: LuaValue?): LuaValue {
        val library = LuaValue.tableOf()
        library.set("sendMessage", sendMessage())
        library.set("sendReplyMessage", sendReplyMessage())
        library.set("getNextMessage", getNextMessage())
        env!!.set("telegram", library)
        return library
    }

    internal class sendMessage : TwoArgFunction() {
        override fun call(id: LuaValue?, message: LuaValue?): LuaValue {
            Bot.server!!.execute(SendMessage(id!!.tolong(), message.toString()))
            return LuaValue.NIL
        }
    }

    internal class sendReplyMessage : ThreeArgFunction() {
        override fun call(id: LuaValue?, replyTo: LuaValue?, message: LuaValue?): LuaValue {
            val reply = replyTo!!.toint()
            Bot.server!!.execute(SendMessage(id!!.tolong(), message.toString()).setReplyToMessageId(reply))
            return LuaValue.NIL
        }
    }

    internal class getNextMessage : OneArgFunction() {
        override fun call(id: LuaValue?): LuaValue {
            if(id != null) {
                val msg = Bot.messageQueue.poll(id.tolong(), TimeUnit.MILLISECONDS) ?: return LuaValue.NIL
                val json = Bot.gson.toJson(msg)
                return LuaValue.valueOf(json)
            }
            return LuaValue.NIL
        }
    }
}