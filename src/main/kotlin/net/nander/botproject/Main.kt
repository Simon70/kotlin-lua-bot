package net.nander.botproject

import com.google.gson.Gson
import org.luaj.vm2.lib.jse.JsePlatform
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
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

    companion object {
        var server: TelegramLongPollingBot? = null
        val messageQueue: LinkedBlockingQueue<Update> = LinkedBlockingQueue()
        val gson = Gson()
    }

    init {
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

        object : Thread() {
            override fun run() {
                while (true) {
                    try {
                        JsePlatform.standardGlobals().load("require 'scripts/bot'.start()").call()
                    } catch (e: Error) {
                        e.printStackTrace()
                    }
                    println("Offline")
                    Thread.sleep(1000)
                    println("Restarting")
                }
            }
        }.start()

    }

    override fun getBotToken(): String {
        return token ?: ""
    }

    override fun getBotUsername(): String {
        return me.userName ?: "NO USERNAME"
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            messageQueue.add(update)
        }
    }
}