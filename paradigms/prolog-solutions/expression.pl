:- load_library('alice.tuprolog.lib.DCGLibrary').

variable(Name, variable(Name)).
const(Value, const(Value)).

op_add(A, B, operation(A, op_add, B)).
op_subtract(A, B, operation(A, op_subtract, B)).
op_multiply(A, B, operation(A, op_multiply, B)).
op_divide(A, B, operation(A, op_divide, B)).


operation(add, A, B, R) :- R is A + B.
operation(subtract, A, B, R) :- R is A - B.
operation(multiply, A, B, R) :- R is A * B.
operation(divide, A, B, R) :- R is A / B.






