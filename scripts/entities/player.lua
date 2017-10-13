--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 13:19
-- To change this template use File | Settings | File Templates.
--

local PLAYER = {}

PLAYER.getPlayer =  function(update)
    local d = DATA.getDataFromChat("Player", update.message)
    if not d then
        d = {name ="YOUR NAME HERE"}
        DATA.setDataFromChat("Player", update.message, d)
    end
    return d
end
return PLAYER