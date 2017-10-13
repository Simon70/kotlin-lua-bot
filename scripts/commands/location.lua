--
-- Defines which commands can be typed in the context of location
--
local LOCATION = {}
local COMMANDS = require 'scripts.helpers.command'
local LLA = require 'scripts.actions.location'

LOCATION[#LOCATION + 1] = { COMMANDS.oneArg("/teleport", type("ASDF")), LLA.teleport }

return LOCATION