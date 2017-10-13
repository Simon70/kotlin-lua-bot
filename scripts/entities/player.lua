--
-- Defines what can be done with a player object. Currently only getPlayer
--

local PLAYER = {}

PLAYER.getPlayer = function(update)
    local d = DATA.getDataFromChat("Player", update)
    if not d then
        d = { name = "YOUR NAME HERE", id = update.message.chat.id }
        DATA.setDataFromChat("Player", update, d)
    end
    return d
end

return PLAYER