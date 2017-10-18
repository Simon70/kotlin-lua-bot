--
-- Main entry point for bot
--
DATA = require('scripts.DATASTORE')
TELEGRAM = require('net.nander.botproject.integrations.Telegram')
json = require 'scripts/DATASTORE'

local bot = {}

local TICKRATE = 100
local helper = require 'scripts.helpers.scripts'

local PLE = require 'scripts.modules.player.entities'
local LLE = require 'scripts.modules.location.entities'
local WLE = require 'scripts.modules.world.entities'

-- Load commands
COMMANDS = {}
local modules = DATA.parse(require 'net.nander.botproject.integrations.GetDirectories'.GET("scripts/modules"))
local private_modules = DATA.parse(require 'net.nander.botproject.integrations.GetDirectories'.GET("scripts/modules_private"))

for _, v in ipairs(modules) do
    helper.addCommands(COMMANDS, require ('scripts.modules.' .. v .. '.commands'))
    print("Loaded module : "..v)
end
for _, v in ipairs(private_modules) do
    helper.addCommands(COMMANDS, require ('scripts.modules_private.' .. v .. '.commands'))
    print("Loaded private module : "..v)
end

local function execute(func, _, var, update, P, L, W)
    if (func.validator(var, update, P, L, W)) then
        print("Calling", func.name)
        return func.call(var, update, P, L, W)
    end
end


-- Main entry point
bot.start = function()
    while (true) do
        local msg = TELEGRAM.getNextMessage(TICKRATE)
        if (msg ~= nil) then
            if bot.cmd(msg) then
                return
            end
        else
            bot.tick()
        end
    end
end

-- Tick
bot.tick = function()
end

-- Execute command
bot.cmd = function(update)
    local update = {text = update[1], chatId = update[2], messageId = update[3] }
    update.text = update.text:gsub("u0027", "'")
    local P = PLE.getPlayer(update)
    local L = LLE.getLocation(P, update)
    local W = WLE.getWorld(P)
    local test = false
    local split = helper.split(update.text)
    for _, v in ipairs(COMMANDS) do
        local b, newSplit = v[1](split, update.text)

        if b then
            local r = execute(v[2], update.text, newSplit, update, P, L, W)
            test = test or r
        end
    end
    return test
end
return bot