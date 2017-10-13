local bot = {}
local DATA = require('scripts.DATASTORE')
local TELEGRAM = require('net.nander.botproject.integrations.Telegram')
local echo = require('scripts.bots.echobot')
-- Bot Commands
bot.cmd = function(update)
    echo(update)

end
return bot