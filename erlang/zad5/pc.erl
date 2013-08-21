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
	prod1 ! stop, unregister (prod1),
	prod2 ! stop, unregister (prod2), 
	prod3 ! stop, unregister (prod3),
	prod4 ! stop, unregister (prod4),
	prod5 ! stop, unregister (prod5),
	prod6 ! stop, unregister (prod6),
	prod7 ! stop, unregister (prod7),
	prod8 ! stop, unregister (prod8),
	prod9 ! stop, unregister (prod9),
	prod10 ! stop, unregister (prod10),
	hurt1 ! stop, unregister (hurt1),
	hurt2 ! stop, unregister (hurt2),
	hurt3 ! stop, unregister (hurt3),
	kons1 ! stop, unregister (kons1),
	kons2 ! stop, unregister (kons2),
	kons3 ! stop, unregister (kons3),
	kons4 ! stop, unregister (kons4),
	kons5 ! stop, unregister (kons5),
	kons6 ! stop, unregister (kons6),
	kons7 ! stop, unregister (kons7),
	kons8 ! stop, unregister (kons8),
	kons9 ! stop, unregister (kons9),
	kons10 ! stop,  unregister (kons10),
	zarzadca ! stop,  unregister (zarzadca).

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