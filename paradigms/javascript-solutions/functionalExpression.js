"use strict";

const operation = f =>  {
    let result = (...exprs) => (x, y, z) => f(...exprs.map(expr => expr(x, y, z)));
    Object.defineProperty(result, "length", { value : f.length });
    return result;
}
const cnst = x => () => x;

const negate = operation(x => -x);

const variable = name => (x, y, z) => name === 'x' ? x : (name === 'y' ? y : z);

const add = operation((a, b) => a + b);

const subtract = operation((a, b) => a - b)

const multiply = operation((a, b) => a * b);

const divide = operation((a, b) => a / b);

const abs = operation(x => x > 0 ? x : -x);

const iff = (expr1, expr2, expr3) => (x, y, z) => expr1(x, y, z) >= 0 ? expr2(x, y, z) : expr3(x, y, z);

const pi = cnst(Math.PI);

const e = cnst(Math.E);

const parse = (string) => {
    const array = string.trim().split(/\s+/);
    let stack = [];
    const parseValue = value => {
        const SIGN_TO_OPERATION = {
            "+": add,
            "-": subtract,
            "*": multiply,
            "/": divide,
            "negate": negate,
            "x": variable,
            "y": variable,
            "z": variable,
            "abs": abs,
            "iff": iff,
            "pi": pi,
            "e": e
        };
        let operation = SIGN_TO_OPERATION[value];
        if (operation === undefined) {
            stack.push(cnst(Number(value)));
        } else if (value === "x" || value === "y" || value === "z") {
            stack.push(operation(value));
        } else if (operation.length === 0) {
            stack.push(operation);
        } else {
            stack.push(operation(...stack.splice(stack.length - operation.length, operation.length)));
        }
    };
    array.map(parseValue);
    return stack.pop();
};
