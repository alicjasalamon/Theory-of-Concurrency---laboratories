-module(pc).

-export([start/0, pInit/0, cInit/0, hurtInit/1]).

start() -> 
	register(hurtownia, spawn(pc, hurtInit, [0])),
	producenci(5),
	konsumenci(5),
	oki.

producenci(N) when (N>0) ->
	spawn(pc, pInit, []),
	producenci(N-1);
producenci(_) -> 
	ok.

konsumenci(N) when (N>0) ->
	spawn(pc, cInit, []),
	konsumenci(N-1);
konsumenci(_) ->
	ok.
	
	
hurtInit(N) when (N==0) ->
	receive
	{Pid, chceProdukowac, Ile} ->
		Pid ! produkuj,
		hurtInit(N+Ile)
	end;
	
hurtInit(N) when (N==20) ->
	receive
	{Pid, chceKonsumowac, Ile} ->
		Pid ! konsumuj,
		hurtInit(N-Ile)
	end;
	
hurtInit(N) ->
	receive
	{Pid, chceKonsumowac, Ile} ->
		Pid ! konsumuj,
		hurtInit(N-Ile);
	{Pid, chceProdukowac, Ile} ->
		Pid ! produkuj,
		hurtInit(N+Ile)
	end.

cInit() ->
	hurtownia ! {self(), chceKonsumowac, random:uniform(10)},
	receive
		konsumuj -> 
			io:format("skonsumowalem\n")
	end.
	
pInit() ->
	hurtownia ! {self(), chceProdukowac, random:uniform(10)},
	receive 
		produkuj -> 
			io:format("wyprodukowalem\n")
	end.

