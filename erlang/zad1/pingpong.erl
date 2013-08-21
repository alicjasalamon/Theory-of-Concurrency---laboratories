-module(pingpong).

-export([start/0,stop/0, play/1, pingInit/0, pongInit/0]).

start() -> 
	register(ping, spawn(pingpong, pingInit, [])),
	register(pong, spawn(pingpong, pongInit, [])),
	ok.

stop() -> 
	ping ! stop,
	pong ! stop,
	ok.
	
play(N) when is_integer(N) and (N > 0) -> 
	ping ! {liczba, N}.

pingInit() ->
	receive
		stop ->
			io:format("KONIEC ZABAWY\n"),
			exit(pong, "ping dostal STOP"),
			ok;
		{liczba, N} when N>0->
			io:format("ping ~p \n", [N]),
			pong ! {liczba, N-1},
			timer:sleep(100),
			pingInit();
		_ ->
			pingInit()
	after 
		20000 ->
			io:format("CZAS SIE SKONCZYL!!\n"),
			ping ! stop
	end.

pongInit() -> 
	receive
		stop ->
			io:format("KONIEC ZABAWY\n"),
			exit(pong, "pong dostal STOP"),
			ok;
		{liczba, N} when N > 0 ->	
			io:format("pong ~p \n", [N]),
			ping ! {liczba, N-1},
			timer:sleep(100),
			pongInit();
		_ -> pongInit()
	after 
		20000 ->
			io:format("CZAS SIE SKONCZYL!!\n"),
			pong ! stop
	end.