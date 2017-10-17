--
-- Defines which commands are defined in the context player
--
local COMMANDS = require 'scripts.helpers.command'
local PLA = require 'scripts.modules.player.actions'
local PLAYER = {}

PLAYER[#PLAYER + 1] = { COMMANDS.zeroArg("/who"), PLA.who }
PLAYER[#PLAYER + 1] = { COMMANDS.arbyArg("/setName"), PLA.setName }

return PLAYER
