-module(tw1c).

-export([start/0, aInit/1, bInit/1, cInit/0]).

start() -> 
	register(a, spawn(tw1c, aInit, [0])),
	register(b, spawn(tw1c, bInit, [0])),
	register(c, spawn(tw1c, cInit, [])),
	ok.
	
aInit(I) ->
	timer:sleep(1000),
	c ! {aaa, I},
	aInit(I+1).

bInit(I) ->
	timer:sleep(1000),
	c ! {bbb, I},
	bInit(I+1).
		
cInit() ->
	receive
		{N, I} -> 
			io:format(N), 
			io:format(" "),
			io:format(I),			
			io:format("\n")
	end,
	cInit().
	