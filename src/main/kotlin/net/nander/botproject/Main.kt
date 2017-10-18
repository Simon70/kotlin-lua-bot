package net.nander.botproject

import com.google.gson.Gson
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.LinkedBlockingQueue

/**
 * Main Entry Point
 */
fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())
}
/**
 * The Bot
 */
class Bot : TelegramLongPollingBot() {
    private var config = File("config.properties")
    private var token: String? = null

    companion object {
        var server: TelegramLongPollingBot? = null
        val messageQueue: LinkedBlockingQueue<LuaValue> = LinkedBlockingQueue()
        val gson = Gson()
    }

    /**
     * Main entry point
     */
    init {

        // Load config
        server = this
        if (config.exists()) {
            val lines = config.readLines(Charset.defaultCharset())
            if (lines.isNotEmpty()) {
                lines.filter { it.toLowerCase().startsWith("token") }
                        .forEach { token = it.substring(it.indexOf("=") + 1) }
            }
        } else {
            config.parentFile.mkdirs()
            config.createNewFile()
            config.writeText("token=\n")
        }
        if (token == null) throw Exception("No token!")
        println("Starting bot...")


        /**
         * Message handling thread: This is where the lua lives.
         */
        object : Thread() {
            override fun run() {
                while (true) {
                    try {
                        JsePlatform.standardGlobals().load("require 'scripts/bot'.start()").call()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                    println("Offline")
                    Thread.sleep(1000)
                    println("Restarting")
                }
            }
        }.start()
    }

    /**
     * Gets the bot-token
     * @return the Token
     */
    override fun getBotToken(): String {
        return token ?: ""
    }

    /**
     * Gets the Username of the bot
     * @return the Username
     */
    override fun getBotUsername(): String {
        return me.userName ?: "NO USERNAME"
    }

    /**
     * Handles a single [Update] from a user
     * @param update the Update to be handled
     */
    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            val l = LuaValue.tableOf()
            if(update.message != null && update.message.text != null) {
                l.insert(1, LuaValue.valueOf(update.message.text))
                l.insert(2, LuaValue.valueOf(update.message.chatId.toDouble()))
                l.insert(3, LuaValue.valueOf(update.message.messageId.toDouble()))

                messageQueue.add(l)

            }

        }
    }
}