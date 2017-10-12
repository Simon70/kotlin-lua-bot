local bot = {}
local DATA = require( 'scripts.DATASTORE' )

-- Bot Commands
bot.cmd = function(update)
    print("FIRST",type(DATA.getData("asdf4","data")))
    DATA.setData("asdf4","data",{asdf="asdf"})
    print("SECOND",DATA.getData("asdf4","data")["asdf"])
end
return bot