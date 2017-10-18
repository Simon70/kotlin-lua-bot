--
-- Defines which actions can be taken within the context of location
--
local DEBUG = {}
local scripts = require 'scripts.helpers.scripts'


-- Restart the server.
DEBUG.restart = {
    name = "restart",
    validator = scripts.onlyHuman,
    call = function(l, _, player, _, _)
        return true
    end
}

return DEBUG