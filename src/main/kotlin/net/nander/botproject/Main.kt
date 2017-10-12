package net.nander.botproject

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.io.File
import java.nio.charset.Charset
import com.google.gson.Gson
import org.luaj.vm2.lib.jse.JsePlatform

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())
}

class Bot : TelegramLongPollingBot() {
    private var config = File("config.properties")
    private var token: String? = null
    private var username: String? = null

    init {
        if (config.exists()) {
            val lines = config.readLines(Charset.defaultCharset())
            if (lines.size >= 2) {
                for (line in lines) {
                    if (line.toLowerCase().startsWith("token"))
                        token = line.substring(line.indexOf("=") + 1)
                    if (line.toLowerCase().startsWith("username"))
                        username = line.substring(line.indexOf("=") + 1)
                }
            }
        } else {
            config.mkdirs()
            config.delete()
            config.createNewFile()
            config.writeText("token=\nusername=\n")
        }
        if (token == null) throw Exception("No token!")
        if (username == null) throw Exception("No Username!")
        println("Starting " + username)
    }

    override fun getBotToken(): String {
        return token ?: ""
    }

    override fun getBotUsername(): String {
        return username ?: ""
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            val gson = Gson()
            val json = gson.toJson(update)
            val globals = JsePlatform.standardGlobals()
            val chunk = globals.load("" +
                    "json = require 'scripts/json'\n" +
                    "bot = require 'scripts/bot'\n" +
                    "local c = (json.parse(({...})[1]))\n" +
                    "bot.cmd(c)")
            chunk.call(json)
        }
    }


}