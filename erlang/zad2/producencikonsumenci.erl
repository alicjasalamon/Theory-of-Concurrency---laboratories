-module(pc).

-export([start/0).
start() -> 
	register(a, spawn(tw1, aInit, [])),
	register(b, spawn(tw1, bInit, [])),
	register(c, spawn(tw1, cInit, [])),
	ok.
