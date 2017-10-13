--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 21:02
-- To change this template use File | Settings | File Templates.
--
local LOCATION = {}
local LLE = require 'scripts.entities.location'
local scripts = require 'scripts.helpers.scripts'

LOCATION.teleport = {
    name = "teleport",
    validator = scripts.onlyHuman,
    call = function(l, update, player, location, world)
        player.location = l[2]
        DATA.setDataFromChat("Player", update, player)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Set Location to " .. l[2])
    end
}
return LOCATION