--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 21:00
-- To change this template use File | Settings | File Templates.
--
local scripts = {}

function scripts.split(str)
    arr = {}
    for i in string.gmatch(str, "%S+") do
        arr[#arr + 1] = i
    end
    return arr
end

function scripts.onlyHuman(_, update)
    return update.message.chat.id > 0
end

function scripts.addCommands(cmd, newc)
    for k, v in ipairs(newc) do
        cmd[#cmd + 1] = v
    end
end

return scripts