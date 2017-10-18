--
-- Defines what can be done with a player object. Currently only getPlayer
--

local PLAYER = {}

-- Gets player data, and creates a player entity if this is the first time this is called.
PLAYER.getPlayer = function(update)
    local d = DATA.getDataFromChat("Player", update)
    if not d then
        d = { name = "YOUR NAME HERE", id = update.chatId }
        DATA.setDataFromChat("Player", update, d)
    end

    return d
end

return PLAYER