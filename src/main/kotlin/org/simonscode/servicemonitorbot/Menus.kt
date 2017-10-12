package org.simonscode.servicemonitorbot

import org.telegram.telegrambots.api.methods.BotApiMethod
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.io.Serializable

object Menus {
    private val menus = mapOf(
            "main" to MainMenu,
            "menu-a" to Menu("Services", InlineKeyboardMarkup().setKeyboard(listOf(listOf(InlineKeyboardButton("MenuB").setCallbackData("menu-b"), InlineKeyboardButton("< Back").setCallbackData("main"))))),
            "menu-b" to Menu("Menu B", InlineKeyboardMarkup().setKeyboard(listOf(listOf(InlineKeyboardButton("< Back").setCallbackData("menu-a")))))
    )
    private val x = Service("")
    private val actions = mapOf(
            "sart" to { "${Math.random()}" },
            "stop" to { "${Math.random()}" },
            "status" to { "${Math.random()}" }
    )

    fun getMainMenu(): SendMessage {
        return SendMessage().setText(MainMenu.text).setReplyMarkup(MainMenu.keyboard)
    }

    fun getNextMenu(callback: CallbackQuery): BotApiMethod<Serializable>? {
        if (menus.containsKey(callback.data)) {
            val menu = menus[callback.data]!!
            return if (menu.text != callback.message.text) {
                EditMessageText().setText(menu.text).setChatId(callback.message.chatId).setReplyMarkup(menu.keyboard)
            } else {
                EditMessageReplyMarkup().setChatId(callback.message.chatId).setReplyMarkup(menu.keyboard)
            }
        } else if (actions.containsKey(callback.data)) {
            val action = actions[callback.data]!!
            return EditMessageText().setText(action())
        }
        return null
    }
}

open class Menu(val text: String, val keyboard: InlineKeyboardMarkup)

object MainMenu : Menu("Main menu", InlineKeyboardMarkup().setKeyboard(listOf(listOf(InlineKeyboardButton("MenuA").setCallbackData("menu-a")))))