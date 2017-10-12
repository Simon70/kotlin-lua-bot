local bot = {}


-- Internal functions.
bot.cmd = function(update)
    local DATA = require( 'scripts.DATASTORE' )
    print("FIRST",type(DATA.getData("asdf4","data")))
    DATA.setData("asdf4","data",{asdf="asdf"})
    print("SECOND",DATA.getData("asdf4","data")["asdf"])
end
return bot