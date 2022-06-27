%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Простые числа - это очень весело %%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

prime(2).

is_prime(N) :- N > 2, \+ 0 is N mod 2, is_prime(N, 3), assert(prime(N)).

is_prime(N, K) :- K * K > N, !.

is_prime(N, K) :- \+ 0 is N mod K, K1 is K + 2, is_prime(N, K1).

composite(N) :-
    N > 1,
    \+ prime(N).

make_prime(N) :- is_prime(N), !.

make_prime(N).

init(MAX_N, N) :- MAX_N < N, !.

init(MAX_N, N) :-
    make_prime(N),
    N1 is N + 1,
    init(MAX_N, N1).

init(MAX_N) :-
    init(MAX_N, 3).

prime_divisors(1, []) :- !.

prime_divisors(N, [H | T]) :- number(N), get_prime_divisors(N, H, 1, T), !.

prime_divisors(N, [H | T]) :- make_prime_divisors(N, H, T), !.

prime_divider(K, K) :- prime(K), !.

prime_divider(N, K) :- number(N), prime(K), 0 is mod(N, K), !.

natural(N, N).

natural(N, K) :- K1 is K + 1, natural(N, K1).

natural(N) :- natural(N,1).

greater(N, R) :- natural(K), R is N + K.

prime_divider(N, K) :- number(K), natural(N1), N is K * N1.

get_prime_divisors(N, N, L, []) :- prime(N), L =<  N, !.

get_prime_divisors(N, K, L, [H | T]) :-
   	prime_divider(N, K),
    L =< K,
    N1 is div(N, K),
    get_prime_divisors(N1, H, K, T), !.

make_prime_divisors(N, K, []) :- prime(K), N = K.

make_prime_divisors(N, K, [H | T]) :-
		make_prime_divisors(N1, H, T),
		prime(K),
		K =< H,
		N is K * N1.

k_notation(0, K, []) :- !.

k_notation(N, K, [H|T] ) :-
	H is mod(N, K),
	N1 is div(N, K),
	k_notation(N1, K, T).

prime_palindrome(N, K) :-
	prime(N),
	k_notation(N, K, R),
	reverse(R, R).
