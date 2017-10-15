--
-- Defines location entity definition. Currently is only a getter
--

local LOCATION = {}

LOCATION.getLocation = function(player)
    local l = player.location or "NULLLAND"
    local d = DATA.getData("Location", l)
    return d
end

return LOCATION