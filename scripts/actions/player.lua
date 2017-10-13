--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 20:59
-- To change this template use File | Settings | File Templates.
--
local PLAYER = {}
local PLE = require 'scripts.entities.player'
local scripts = require 'scripts.helpers.scripts'
PLAYER.setName = {
    validator = scripts.onlyHuman,
    call = function(l, update)
        local d = PLE.getPlayer(update)
        d.name = l[2]
        DATA.setDataFromChat("Player", update.message, d)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "SET NAME TO " .. l[2])
    end
}
PLAYER.who = {
    validator = scripts.onlyHuman,
    call = function(l, update)
        local d = PLE.getPlayer(update)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Your name is " .. d.name)
    end
}
return PLAYER
