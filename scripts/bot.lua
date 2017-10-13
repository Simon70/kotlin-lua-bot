local bot = {}
DATA = require('scripts.DATASTORE')
TELEGRAM = require('net.nander.botproject.integrations.Telegram')

local commands = {}
local helper = require 'scripts.helpers.scripts'

helper.addCommands(commands,require 'scripts.commands.player')
helper.addCommands(commands,require 'scripts.commands.location')
local PLE = require 'scripts.entities.player'
local LLE = require 'scripts.entities.location'


-- Bot Commands

local function execute  (func, var, update, P, L, W)
    if(func.validator(var, update, P, L, W)) then
        func.call(var,update, P, L, W)
        print("Calling", func.name)
    end
end
bot.cmd = function(update)
    local P = PLE.getPlayer(update)
    local L = LLE.getLocation(P)
    if not update.message.text then
        print("Thanks for trying")
        return
    end
    local var = helper.split(update.message.text)
    for _, v in ipairs(commands) do
        local b, varr = v[1](var)
        if b then
            execute(v[2], varr, update, P, L, nil)
        end
    end
end
return bot