--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 20:59
-- To change this template use File | Settings | File Templates.
--
local PLAYER = {}
local scripts = require 'scripts.helpers.scripts'
PLAYER.setName = { name="setName",
    validator = scripts.onlyHuman,
    call = function(l, update, player, location, world)
        player.name = l[2]
        DATA.setDataFromChat("Player", update, player)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "SET NAME TO " .. player.name)
    end
}
PLAYER.who = { name="who",
    validator = scripts.onlyHuman,
    call = function(l, update, player, location, world)
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Your name is " .. player.name)
    end
}
return PLAYER
