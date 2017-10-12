local bot = {}


-- Internal functions.
bot.cmd = function(update)
    local DATA = require( 'net.nander.botproject.integrations.Mongol' )
    local URL = require( 'net.nander.botproject.integrations.GetUrl' )
    print(URL.GET("https://nander.net"))
    print("FIRST",type(DATA.getData("asdf4","data")))
    DATA.setData("asdf4","data","{'test'=2}")
    print("SECOND",DATA.getData("asdf4","data"))
end
return bot