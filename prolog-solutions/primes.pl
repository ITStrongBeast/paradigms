prime(N) :- number(N), N > 1, prime_recur(N, 2).

composite(N) :- \+ prime(N).

prime_divisors(N, L) :- number(N), divisors(N, L).

prime_recur(N, R) :- 
	R > sqrt(N) ;
	(\+ 0 is mod(N, R), 
	R1 is R + 1,
	prime_recur(N, R1)). 
	
is_mod(N, R, S) :-
	(R = S, 0 is mod(N, S)) ;
	(S1 is S + 1, 
	S2 is S * S,
	N > S2, 
	is_mod(N, R, S1)).	

divisors(1, []) :- !.
divisors(N, [H | T]) :- 
    (prime(N), [N] = [H | T]), ! ; 
    (is_mod(N, H, 2), R is N // H, divisors(R, T)), !.

nth_prime_recur(N, P, S, R) :- N = S, R = P, !.

nth_prime_recur(N, P, S, R) :-
	(\+ prime(P),
	P1 is P + 1,
	nth_prime_recur(N, P1, S, R)) ;
	(prime(P),
	N \= S,
	S1 is S + 1,
	P1 is P + 1,
	nth_prime_recur(N, P1, S1, R)).	

nth_prime(N, R) :- P1 is 0, nth_prime_recur(N, P1, 0, R2) , R is R2 - 1, !.

	