--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 20:59
-- To change this template use File | Settings | File Templates.
--
local COMMANDS = require 'scripts.helpers.command'
local PLA      = require 'scripts.actions.player'
local PLAYER = {}

PLAYER[#PLAYER+1] = {COMMANDS.zeroArg("/who"), PLA.who }
PLAYER[#PLAYER+1] = {COMMANDS.arbyArg("/setName"), PLA.setName}

return PLAYER
