--
-- Defines location entity definition. Currently is only a getter
--

local LOCATION = {}

LOCATION.getLocation = function(player, update)
    local l = player.location or "NULL"
    local d = DATA.getData("Location", l)
    if not d then
        local err
        print(l)
        d = pcall(function() return require('scripts.locations.' .. l) end)
        if not d then
            player.location = "university.zilverling.iapc"
            print("THERE")
            DATA.setDataFromChat("Player", update, player)
            return {}
        end
    end
    return d
end

return LOCATION