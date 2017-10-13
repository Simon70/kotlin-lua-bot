--
-- Defines what can be done with a player object. Currently only getPlayer
--

local PLAYER = {}

PLAYER.getPlayer = function(update)
    print("inb4")
    local d = DATA.getDataFromChat("Player", update)
    print("inb4")
    if not d then
        d = { name = "YOUR NAME HERE" }
        DATA.setDataFromChat("Player", update, d)
    end
    return d
end

return PLAYER