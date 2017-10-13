--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 21:02
-- To change this template use File | Settings | File Templates.
--
local LOCATION = {}
local LLE = require 'scripts.entities.location'

LOCATION.teleport =  function(player, update)
    local l = scripts.split(update.message.text)
    if not (l[1] == "/teleport" and #l == 2) then
        return
    end

    player.location = l[2]
    DATA.setDataFromChat("Player", update.message, d)
    TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Set Location to "..l[2])
end

LOCATION.create =  function(update)
    local l = scripts.split(update.message.text)
    if not (l[1] == "/create" ) then
        return
    end

    DATA.setDataFromChat("Location", update.message, l[2])
    TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Your name is "..d.name)
end

return PLAYER