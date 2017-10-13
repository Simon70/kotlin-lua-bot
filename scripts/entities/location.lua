--
-- Defines location entity definition. Currently is only a getter
--

local scripts = require 'scripts.helpers.scripts'
local LOCATION = {}

LOCATION.getLocation = function(player)
    local l = player.location or "NULLLAND"
    local d = DATA.getData("Location", player.location)
    return d
end

return LOCATION