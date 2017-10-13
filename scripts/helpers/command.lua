--
-- Defines helper-function-generators for type-check functions
--

local COMMANDTYPES = {}

COMMANDTYPES.zeroArg = function(arg)
    return function(var)
        return #var == 1 and var[1] == arg, var
    end
end

COMMANDTYPES.oneArg = function(arg, typ)
    return function(var)
        return #var == 2 and var[1] == arg and type(var[1]) == typ, var
    end
end

COMMANDTYPES.arbyArg = function(arg)
    return function(var)
        if #var <= 1 or var[1] ~= arg then
            return false
        end
        local s = var[2]
        for i = 3, #var do
            s = s .. " " .. var[i]
        end
        return true, { var[1], s }
    end
end

return COMMANDTYPES