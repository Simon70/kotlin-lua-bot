local bot = {}
DATA = require('scripts.DATASTORE')
TELEGRAM = require('net.nander.botproject.integrations.Telegram')

local commands = require 'scripts.commands.player'
local helper = require 'scripts.helpers.scripts'

-- Bot Commands

local function execute  (func, var, update)
    if(func.validator(var, update)) then
        func.call(var,update)
    end
end
bot.cmd = function(update)
    if not update.message.text then
        print("Thanks for trying")
        return
    end
    local var = helper.split(update.message.text)
    for _, v in ipairs(commands) do
        local b, varr = v[1](var)
        if b then
            execute(v[2], varr, update)
        end
    end
end
return bot