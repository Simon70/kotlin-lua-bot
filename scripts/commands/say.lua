--
-- Defines which commands are defined in the context player
--
local COMMANDS = require 'scripts.helpers.command'
local SLA = require 'scripts.actions.say'
local SAY = {}

SAY[#SAY + 1] = { COMMANDS.arbyArg("/say"), SLA.say }

return SAY
