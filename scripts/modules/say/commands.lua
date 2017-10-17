--
-- Defines which commands are defined in the context player
--
local COMMANDS = require 'scripts.helpers.command'
local SLA = require 'scripts.modules.say.actions'
local SAY = {}

SAY[#SAY + 1] = { COMMANDS.noCommand(), SLA.say }

return SAY
