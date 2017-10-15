package net.nander.botproject

import com.google.gson.Gson
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.luaj.vm2.lib.jse.JsePlatform
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.LinkedBlockingQueue

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())
}

class Bot : TelegramLongPollingBot() {
    private var config = File("config.properties")
    private var token: String? = null
    private var username: String? = null

    companion object {
        var server: TelegramLongPollingBot? = null
        val messageQueue: LinkedBlockingQueue<Update> = LinkedBlockingQueue()
        val gson = Gson()

    }

    init {
        server = this
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

        object : Thread() {
            override fun run() {
                val globals = JsePlatform.standardGlobals()

                val chunk = globals.load("" +
                        "json = require 'scripts/DATASTORE'\n" +
                        "bot = require 'scripts/bot'\n" +
                        "local c = (json.parse(({...})[1]))\n" +
                        "bot.start()")
                chunk.call()            }
        }.start()

    }

    override fun getBotToken(): String {
        return token ?: ""
    }

    override fun getBotUsername(): String {
        return username ?: ""
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            messageQueue.add(update)
        }
    }
}