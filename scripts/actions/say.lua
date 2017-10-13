--
-- Defines which actions can be taken within the context of location
--
local SAY = {}
local scripts = require 'scripts.helpers.scripts'

SAY.say = {
    name = "say",
    validator = scripts.onlyHuman,
    call = function(l, update, player, location, world)
        local d = DATA.getAllData("Player")
        for k, v in pairs(d) do
            print(v.location, player.location)
            if (v.location == player.location) then
                TELEGRAM.sendMessage(update.message.chat.id, player.name..": "..l[2])
            end
        end
    end
}



return SAY