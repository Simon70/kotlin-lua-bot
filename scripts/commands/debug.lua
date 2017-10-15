--
-- Defines which commands are defined in the context player
--
local COMMANDS = require 'scripts.helpers.command'
local DLA = require 'scripts.actions.debug'
local DEBUG = {}

DEBUG[#DEBUG + 1] = { COMMANDS.zeroArg("/restart451"), DLA.restart }

return DEBUG
