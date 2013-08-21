-module(pc).

-export([start/0, pInit/0, kInit/0, hurtInit/1, stop/0]).

start() -> 
	register(hurtownia, spawn(pc, hurtInit, [0])),
	producenci(5),
	konsumenci(5),
	oki.

producenci(N) when (N>0) ->
	register(list_to_atom("prod" ++ integer_to_list(N)) ,spawn(pc, pInit, [])),
	producenci(N-1);
producenci(_) -> 
	ok.

konsumenci(N) when (N>0) ->
		register(list_to_atom("kons" ++ integer_to_list(N)), spawn(pc, kInit, [])),
	konsumenci(N-1);
konsumenci(_) ->
	ok.
	
	
hurtInit(N) when (N<10) ->
	receive
	{Pid, chceProdukowac, Ile} ->
		Pid ! produkuj,
		hurtInit(N+Ile);
	stop 
		-> ok
	end;
	
hurtInit(N) when (N>=10) ->
	receive
	{Pid, chceKonsumowac, Ile} ->
		Pid ! konsumuj,
		hurtInit(N-Ile);
	stop 
		-> ok
	end;
	
hurtInit(N) ->
	receive
	{Pid, chceKonsumowac, Ile} ->
		Pid ! konsumuj,
		hurtInit(N-Ile);
	{Pid, chceProdukowac, Ile} ->
		Pid ! produkuj,
		hurtInit(N+Ile);
	stop 
		-> ok
	end.

kInit() ->
	timer:sleep(random:uniform(3000)),
	hurtownia ! {self(), chceKonsumowac, random:uniform(10)},
	receive
		konsumuj -> 
			io:format(":) \t"),
			timer:sleep(3000),
			kInit();
		stop 
			-> ok
	end.
	
pInit() ->
	timer:sleep(random:uniform(3000)),
	hurtownia ! {self(), chceProdukowac, random:uniform(10)},
	receive 
		produkuj -> 
			io:format("(: \t"),
			timer:sleep(3000),
			pInit();
		stop 
			-> ok
	end.

stop() ->
	prod1 ! stop,
	prod2 ! stop,
	prod3 ! stop,
	prod4 ! stop,
	prod5 ! stop,
	kons1 ! stop,
	kons2 ! stop,
	kons3 ! stop,
	kons4 ! stop,
	kons5 ! stop,
	hurtownia ! stop.
	