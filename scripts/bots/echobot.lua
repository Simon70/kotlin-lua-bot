--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 13:19
-- To change this template use File | Settings | File Templates.
--
local DATA = require('scripts.DATASTORE')
local TELEGRAM = require('net.nander.botproject.integrations.Telegram')


return function(update)
    if update.message.chat.id == -1001050885996 then
        return
    end
    if (update.message.text == "@echo on") then
        DATA.setDataFromChat("@echo", update.message, { true })
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Echo turned on")
        return
    end
    if (update.message.text == "@echo off") then
        DATA.setDataFromChat("@echo", update.message, { false })
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, "Echo turned off")
        return
    end
    local d = DATA.getDataFromChat("@echo", update.message)
    if d and d[1] then
        TELEGRAM.sendReplyMessage(update.message.chat.id, update.message.messageId, update.message.text)
    end
end

