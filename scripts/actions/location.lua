--
-- Defines which actions can be taken within the context of location
--
local LOCATION = {}
local LLE = require 'scripts.entities.location'
local scripts = require 'scripts.helpers.scripts'

LOCATION.teleport = {
    name = "teleport",
    validator = scripts.onlyHuman,
    call = function(l, update, player, _, _)
        player.location = l[2]
        DATA.setDataFromChat("Player", update, player)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Set Location to " .. l[2])
    end
}

-- TODO
LOCATION.move = {
    name = "move",
    validator = scripts.onlyHuman,
    call = function(l, update, player, _, _)
        player.location = l[2]
        DATA.setDataFromChat("Player", update, player)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Set Location to " .. l[2])
    end
}

return LOCATION