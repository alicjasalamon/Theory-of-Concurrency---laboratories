-module(tw1).

-export([start/0, aInit/0, bInit/0, cInit/0]).

start() -> 
	register(a, spawn(tw1, aInit, [])),
	register(b, spawn(tw1, bInit, [])),
	register(c, spawn(tw1, cInit, [])),
	ok.
	
aInit() ->
	timer:sleep(1000),
	c ! aaa,
	aInit().

bInit() ->
	timer:sleep(1000),
	c ! bbb,
	bInit().
		
cInit() ->
	receive
		_ -> io:format(":) ")
	end,
	cInit().
	

