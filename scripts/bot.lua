--
-- Main entry point for bot
--
local TICKRATE = 100
local bot = {}
DATA = require('scripts.DATASTORE')
TELEGRAM = require('net.nander.botproject.integrations.Telegram')
json = require 'scripts/DATASTORE'

local commands = {}
local helper = require 'scripts.helpers.scripts'
local a = DATA.parse(require 'net.nander.botproject.integrations.GetDirectories'.GET("modules"))
local b = DATA.parse(require 'net.nander.botproject.integrations.GetDirectories'.GET("modules_private"))
for _, v in ipairs(a) do
    helper.addCommands(commands, require ('scripts.modules.' .. v .. '.commands'))
    print("Loaded module : "..v)
end
for _, v in ipairs(b) do
    helper.addCommands(commands, require ('scripts.modules_private.' .. v .. '.commands'))
    print("Loaded private module : "..v)
end
local PLE = require 'scripts.modules.player.entities'
local LLE = require 'scripts.modules.location.entities'
local WLE = require 'scripts.modules.world.entities'

local function execute(func, _, var, update, P, L, W)
    if (func.validator(var, update, P, L, W)) then
        print("Calling", func.name)
        return func.call(var, update, P, L, W)
    end
end

bot.start = function()
    while (true) do
        local msg = json.parse(TELEGRAM.getNextMessage(TICKRATE))
        if (msg ~= nil) then
            if bot.cmd(msg) then
                return
            end
        else
            bot.tick()
        end
    end
end

bot.tick = function()
end

bot.cmd = function(update)
    if not update or not update.message or not update.message.text then
        print("Thanks for trying")
        return
    end
    update.message.text = update.message.text:gsub("u0027", "'")

    local P = PLE.getPlayer(update)
    local L = LLE.getLocation(P)
    local W = WLE.getWorld(P)
    local test = false
    local split = helper.split(update.message.text)
    for _, v in ipairs(commands) do
        local b, newSplit = v[1](split, update.message.text)

        if b then
            local r = execute(v[2], update.message.text, newSplit, update, P, L, W)
            test = test or r
        end
    end
    return test
end
return bot