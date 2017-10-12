package org.simonscode.servicemonitorbot

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.io.File
import java.nio.charset.Charset

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())
}

class Bot : TelegramLongPollingBot() {
    private var config = File("config.properties")
    private var token: String? = null
    private var username: String? = null
    override fun getBotToken(): String {
        return token ?: ""
    }

    override fun getBotUsername(): String {
        return username ?: ""
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            when {
                update.hasMessage() -> handle(update.message)
                update.hasCallbackQuery() -> handle(update.callbackQuery)
                else -> println(update)
            }
        }
    }

    private fun handle(callbackQuery: CallbackQuery) {
        val nextMenu = Menus.getNextMenu(callbackQuery)
        if (nextMenu != null) {
            if (nextMenu is EditMessageReplyMarkup)
                execute(nextMenu.setChatId(callbackQuery.message.chatId).setMessageId(callbackQuery.message.messageId))
            else if (nextMenu is EditMessageText)
                execute(nextMenu.setChatId(callbackQuery.message.chatId).setMessageId(callbackQuery.message.messageId))
        } else {
            execute(SendMessage().setChatId(callbackQuery.message.chatId).setText("Error"))
        }
    }

    private fun handle(message: Message) {
        if (message.text == "/services") {
            execute(Menus.getMainMenu().setChatId(message.chatId))
        }
    }

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

}