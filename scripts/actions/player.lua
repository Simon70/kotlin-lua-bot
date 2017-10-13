--
-- Defines which actions can be taken within the context of player
--
local PLAYER = {}
local scripts = require 'scripts.helpers.scripts'

PLAYER.setName = {
    name = "setName",
    validator = scripts.onlyHuman,
    call = function(l, update, player, location, world)
        player.name = l[2]
        DATA.setDataFromChat("Player", update, player)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "SET NAME TO " .. player.name)
    end
}

PLAYER.who = {
    name = "who",
    validator = scripts.onlyHuman,
    call = function(l, update, player, location, world)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Your name is " .. player.name)
    end
}

return PLAYER
