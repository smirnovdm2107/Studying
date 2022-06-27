
recount_height(N1, N2, HN) :- height(N1, H1), height(N2, H2), max(H1, H2, R), HN is R + 1.

max(H1, H2, H2) :- H1 =< H2, !.

max(H1, H2, H1) :- H1 > H2.

height(nil, 0).

height(node(_, _, _, _, H), H).

map_get(node(Key, Value, _, _, _),Key, Value) :- !.

map_get(node(K, V, L, R, H), Key, Result) :- K < Key, map_get(R, Key, Result), !.

map_get(node(K, V, L, R, H), Key, Result) :- K > Key, map_get(L, Key, Result), !.

remove_min_and_return(node(K, V, nil, R, H), R, K, V) :- !.

remove_min_and_return(node(K, V, L, R, H), node(K, V, Result, R, HN), Key, Value) :-
        remove_min_and_return(L, Result, Key, Value),
        recount_height(Result, R, HN).

map_remove(nil, Key, nil) :- !.

map_remove(node(K, V, nil, R, H),K, R) :- !.

map_remove(node(K, V, L, nil, H), K, L) :- !.

map_remove(node(K, V, L, R, H), K, Result1) :-
         remove_min_and_return(R, Result, Key, Value),
         recount_height(L, Result, HN),
         check_balance(node(Key, Value, L, Result, HN), Result1), !.

map_remove(node(K, V, L, R, H), Key, Result1) :-
          K < Key,
          map_remove(R, Key, Result),
          recount_height(L, Result, HN),
          check_balance(node(K, V, L, Result, HN), Result1), !.

map_remove(node(K, V, L, R, H), Key, Result1) :-
        K > Key,
        map_remove(L, Key, Result),
        recount_height(Result, R, HN),
        check_balance(node(K, V, Result, R, HN), Result1), !.

map_put(nil, Key, Value, node(Key, Value, nil, nil, 1)).

map_put(node(Key, V, L, R, H), Key, Value, node(Key, Value, L, R, H)).

map_put(node(K, V, L, R, H), Key, Value, Result1) :-
        K < Key,
        map_put(R, Key, Value, Result),
        recount_height(L, Result, DH),
        check_balance(node(K, V, L, Result, DH), Result1), !.

map_put(node(K, V, L, R, H), Key, Value, Result1) :-
        K > Key,
        map_put(L, Key, Value, Result),
        recount_height(Result, R, DH),
        check_balance(node(K, V, Result, R, DH), Result1), !.

map_build([], nil) :- !.

map_build([(Key, Value) | T], Result) :- map_build(T, TreeMap), map_put(TreeMap, Key, Value, Result).

diff(nil, nil) :- !.

diff(node(K, V, L, R, H), Result) :- height(L, Result1), height(R, Result2), Result is Result1 - Result2.

check_balance(Node, Result) :-
        Node = node(K, V, L, R, H),
        diff(Node, D), diff(L,DL),
        diff(R, DR), check_balance(Node, D, DL, DR, Result), !.

check_balance(Node, D, DL, DR, Node) :- -1 =< D, D =< 1, !.

check_balance(Node, D, DL, DR, Result) :- D = -2, \+ DR = 1, rotate_left(Node, Result), !.

check_balance(Node, D, DL, DR, Result) :- D = -2, DR = 1, big_rotate_left(Node, Result), !.

check_balance(Node, D, DL, DR, Result) :- D = 2, \+ DL = -1, rotate_right(Node, Result), !.

check_balance(Node, D, DL, DR, Result) :- D = 2, DL = -1, big_rotate_right(Node, Result), !.

rotate_left(node(K1, V1, L1, node(K2, V2, L2, R2, H2), H1), node(K2, V2, node(K1, V1, L1, L2, HN2), R2, HN1)) :-
            recount_height(L1, L2, HN2),
            recount_height(node(K1, V1, L1, L2, HN2), R2, HN1).
rotate_right(node(K1, V1, node(K2, V2, L2, R2, H2), R1, H1), node(K2, V2, L2, node(K1, V1, R2, R1, HN2), HN1)) :-
            recount_height(R1, R2, HN2),
            recount_height(L2, node(K1, V1, R2, R1, HN2), HN1).
big_rotate_left(node(K, V, L, R, H), Result) :- rotate_right(R, Result1), rotate_left(node(K, V, L, Result1, H), Result).
big_rotate_right(node(K, V, L, R, H), Result) :- rotate_left(L, Result1), rotate_right(node(K, V, Result1, R, H), Result).

map_floorKey(node(Key, Value, _, _, _), Key, Key) :- !.

map_floorKey(node(K, _, _, R, _), Key, Result) :- K < Key, map_floorKey(R, Key, Result), !.

map_floorKey(node(K, V, L, R, H), Key, K) :- K < Key, !.

map_floorKey(node(K, V, L, R, H), Key, Result) :- K > Key, map_floorKey(L, Key, Result), !.
