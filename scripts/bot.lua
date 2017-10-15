--
-- Main entry point for bot
--
local TICKRATE = 100
local bot = {}
DATA = require('scripts.DATASTORE')
TELEGRAM = require('net.nander.botproject.integrations.Telegram')

local commands = {}
local helper = require 'scripts.helpers.scripts'
helper.addCommands(commands, require 'scripts.commands.say')
helper.addCommands(commands, require 'scripts.commands.player')
helper.addCommands(commands, require 'scripts.commands.location')
local PLE = require 'scripts.entities.player'
local LLE = require 'scripts.entities.location'
local WLE = require 'scripts.entities.world'

local function execute(func, _, var, update, P, L, W)
    if (func.validator(var, update, P, L, W)) then
        func.call(var, update, P, L, W)
        print("Calling", func.name)
    end
end

bot.start = function()
    while(true) do
        local msg = json.parse(TELEGRAM.getNextMessage(TICKRATE))
        if(msg ~= nil) then
            bot.cmd(msg)
        else
            bot.tick()
        end
    end
end

bot.tick = function()
end

bot.cmd = function(update)
    if not update.message.text then
        print("Thanks for trying")
        return
    end
    update.message.text = update.message.text:gsub("u0027", "'")

    local P = PLE.getPlayer(update)
    local L = LLE.getLocation(P)
    local W = WLE.getWorld(P)

    local split = helper.split(update.message.text)
    for _, v in ipairs(commands) do
        local b, newSplit = v[1](split, update.message.text)
        if b then
            execute(v[2], update.message.text, newSplit, update, P, L, W)
        end
    end
end
return bot