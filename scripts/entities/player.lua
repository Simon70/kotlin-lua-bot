--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 13:19
-- To change this template use File | Settings | File Templates.
--

local PLAYER = {}

PLAYER.getPlayer =  function(update)
    print("inb4")
    local d = DATA.getDataFromChat("Player", update)
    print("inb4")
    if not d then
        d = {name ="YOUR NAME HERE"}
        DATA.setDataFromChat("Player", update, d)
    end
    return d
end
return PLAYER