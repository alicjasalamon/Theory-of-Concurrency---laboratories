-module(tw1a).

-export([start/0, aInit/0, bInit/0, cInit/0]).

start() -> 
	register(a, spawn(tw1a, aInit, [])),
	register(b, spawn(tw1a, bInit, [])),
	register(c, spawn(tw1a, cInit, [])),
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
		aaa -> io:format(":) \n")
	end,
	receive
		bbb -> io:format(":( \n")
	end,
	cInit().
	

