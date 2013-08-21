-module(pc).

-export([start/0, pInitInit/0, kInitInit/0, hurtInit/1, stop/0, zarzInit/4]).

start() -> 
	register(zarzadca ,spawn(pc, zarzInit, [0,0,0, miesz])),
	hurtownie(3),
	producenci(10),
	konsumenci(10),
	oki.

producenci(N) when (N>0) ->
	register(list_to_atom("prod" ++ integer_to_list(N)) ,spawn(pc, pInitInit, [])),
	producenci(N-1);
producenci(_) -> 
	ok.

konsumenci(N) when (N>0) ->
	register(list_to_atom("kons" ++ integer_to_list(N)), spawn(pc, kInitInit, [])),
	konsumenci(N-1);
konsumenci(_) ->
	ok.
	
hurtownie(N) when (N>0) ->
	register(list_to_atom("hurt" ++ integer_to_list(N)), spawn(pc, hurtInit, [0])),
	hurtownie(N-1);
hurtownie(_) ->
	ok.

zarzInit(A,B,C, miesz) ->
	receive
		{Pid, chceProdukowac, Ile} when (A + Ile =< 20) ->
			io:format("prod ~p ~p ~n", [Ile, 1]),
			hurt1 ! {Pid, chceProdukowac, Ile},
			zarzInit(A+Ile, B, C, miesz);
		{Pid, chceProdukowac, Ile} when (B + Ile =< 20) ->			
			io:format("prod ~p ~p ~n", [Ile, 2]),
			hurt2 ! {Pid, chceProdukowac, Ile},
			zarzInit(A, B+Ile, C, miesz);
		{Pid, chceProdukowac, Ile} when (C + Ile =< 20) ->
			io:format("prod ~p ~p ~n", [Ile, 3]),
			hurt3 ! {Pid, chceProdukowac, Ile},
			zarzInit(A, B, C+Ile, miesz);
		{Pid, chceProdukowac, Ile } ->
			zarzInit(A, B, C, {konsum, Pid, Ile});
		{Pid, chceKonsumowac, Ile} when (C - Ile >= 0) ->
			io:format("kons ~p ~p ~n", [Ile, 3]),
			hurt3 ! {Pid, chceKonsumowac, Ile},
			zarzInit(A, B, C-Ile, miesz);
		{Pid, chceKonsumowac, Ile} when (B - Ile >= 0) ->
			io:format("kons ~p ~p ~n", [Ile, 2]),
			hurt3 ! {Pid, chceKonsumowac, Ile},
			zarzInit(A, B-Ile, C, miesz);
		{Pid, chceKonsumowac, Ile} when (A - Ile >= 0) ->
			io:format("kons ~p ~p ~n", [Ile, 1]),
			hurt3 ! {Pid, chceKonsumowac, Ile},
			zarzInit(A-Ile, B, C, miesz);
		{Pid, chceKonsumowac, Ile} ->
			zarzInit(A, B, C, {produc, Pid, Ile});
		stop ->
			ok;
		_ -> 
			io:format("what?\n"), ok
	end;
	
zarzInit(A,B,C, {konsum, PidProd, IleProd}) ->
	receive 
		{Pid, chceKonsumowac, Ile} when (C - Ile >= 0), (C-Ile+IleProd =< 20) ->
			io:format("kons ~p ~p ~n", [Ile, 3]),
			hurt3 ! {Pid, chceKonsumowac, Ile},
			io:format("prod ~p ~p ~n", [IleProd, 3]),
			hurt3 ! {PidProd, chceProdukowac, IleProd},
			zarzInit(A, B, C-Ile+IleProd, miesz);
		{Pid, chceKonsumowac, Ile} when (C - Ile >= 0) ->
			io:format("kons ~p ~p ~n", [Ile, 3]),
			hurt3 ! {Pid, chceKonsumowac, Ile},
			zarzInit(A, B, C-Ile, {konsum, PidProd, IleProd});
		stop ->
			ok
	end;
	
zarzInit(A,B,C, {produc, PidKons, IleKons}) ->
	receive
		{Pid, chceProdukowac, Ile} when (A + Ile =< 20) , ( A+Ile-IleKons >= 0) ->
			io:format("prod ~p ~p ~n", [Ile, 1]),
			hurt1 ! {Pid, chceProdukowac, Ile},
			io:format("kons ~p ~p ~n", [IleKons, 1]),
			hurt1 ! {PidKons, chceKonsumowac, IleKons},
			zarzInit(A+Ile-IleKons, B,  C, miesz);
		{Pid, chceProdukowac, Ile} when (A + Ile =< 20) ->
			io:format("prod ~p ~p ~n", [Ile, 1]),
			hurt1 ! {Pid, chceProdukowac, Ile},
			zarzInit(A+Ile, B,  C, {produc, PidKons, IleKons});
		stop ->
			ok 
 	end.

	
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
	N = random:uniform(10),
	zarzadca ! {self(), chceKonsumowac, N},
	receive
		konsumuj -> 
			%%io:format("-" ++ integer_to_list(N) ++ "\t" ),
			timer:sleep(3000),
			kInit();
		stop 
			-> ok
	end.
	
pInit() ->
	timer:sleep(random:uniform(3000)),
	N = random:uniform(10),
	zarzadca ! {self(), chceProdukowac, N},
	receive 
		produkuj -> 
			%%io:format("+" ++ integer_to_list(N) ++ "\t" ),
			timer:sleep(3000),
			pInit();
		stop -> 
			ok
	end.

stop() ->
	prod1 ! stop,
	prod2 ! stop,
	prod3 ! stop,
	prod4 ! stop,
	prod5 ! stop,	
	prod6 ! stop,
	prod7 ! stop,
	prod8 ! stop,
	prod9 ! stop,
	prod10 ! stop,
	hurt1 ! stop,
	hurt2 ! stop,
	hurt3 ! stop,
	kons1 ! stop,
	kons2 ! stop,
	kons3 ! stop,
	kons4 ! stop,
	kons5 ! stop,
	kons6 ! stop,
	kons7 ! stop,
	kons8 ! stop,
	kons9 ! stop,
	kons10 ! stop,
	zarzadca ! stop.

pInitInit() ->
	N = crypto:rand_bytes(12),
	<<A:32,B:32,C:32>> = N,
	random:seed(A,B,C),
	pInit().


kInitInit() ->
	N = crypto:rand_bytes(12),
	<<A:32,B:32,C:32>> = N,
	random:seed(A,B,C),
	kInit().