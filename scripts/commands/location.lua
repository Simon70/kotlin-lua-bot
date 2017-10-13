--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 21:02
-- To change this template use File | Settings | File Templates.
--
local LOCATION = {}
local COMMANDS = require 'scripts.helpers.command'
local LLA = require 'scripts.actions.location'

LOCATION[#LOCATION + 1] = { COMMANDS.oneArg("/teleport", type("ASDF")), LLA.teleport }

return LOCATION