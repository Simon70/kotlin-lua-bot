--
-- Created by IntelliJ IDEA.
-- User: nander
-- Date: 13-10-17
-- Time: 21:26
-- To change this template use File | Settings | File Templates.
--

local COMMANDTYPES = {}

COMMANDTYPES.zeroArg = function(arg)
    return function(var)
        print(arg, var, #var == 1 and var[1] == arg)
        return #var == 1 and var[1] == arg, var
    end
end

COMMANDTYPES.arbyArg = function(arg)
    return function(var)
        if #var <= 1 or var[1] ~= arg then
            return false
        end
        local s = var[2]
        for i=3, #var do
            s = s .. " "..var[i]
        end
        return true, {var[1], s}
    end
end
return COMMANDTYPES