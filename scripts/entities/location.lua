--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 13:19
-- To change this template use File | Settings | File Templates.
--

local scripts = require 'scripts.helpers.scripts'
local LOCATION = {}

LOCATION.getLocation = function(player)
    local l = player.location or "NULLLAND"
    local d = DATA.getData("Location", player.location)
    return d
end

return LOCATION