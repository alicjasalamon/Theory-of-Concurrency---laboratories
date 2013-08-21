-module(tw1b).

-export([start/0, aInit/0, bInit/0, cInit/0]).

start() -> 
	register(a, spawn(tw1b, aInit, [])),
	register(b, spawn(tw1b, bInit, [])),
	register(c, spawn(tw1b, cInit, [])),
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
		N -> 
		io:format(N),
		io:format("\n")
	end,
	cInit().
	

