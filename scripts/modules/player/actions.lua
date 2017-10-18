--
-- Defines which actions can be taken within the context of player
--
local PLAYER = {}
local scripts = require 'scripts.helpers.scripts'

-- Set the name of the player.
PLAYER.setName = {
    name = "setName",
    validator = scripts.onlyHuman,
    call = function(l, update, player, _, _)
        player.name = l[2]
        player.id = update.message.chat.id
        DATA.setDataFromChat("Player", update, player)
        TELEGRAM.sendReplyMessage(update.chatId, update.messageId, "SET NAME TO " .. player.name)
    end
}

-- get the name of the player
PLAYER.who = {
    name = "who",
    validator = scripts.onlyHuman,
    call = function(_, update, player, _, _)
        TELEGRAM.sendReplyMessage(update.chatId, update.messageId, "Your name is " .. player.name)
    end
}

return PLAYER
