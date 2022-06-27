const VARIABLE_NAMES = {
    'x': 0,
    'y': 1,
    'z': 2
};

function Const(num) {
    this.num = num;
}

Const.ZERO = new Const(0);
Const.E = new Const(Math.E);
Const.ONE = new Const(1);
Const.TWO = new Const(2);

Const.prototype.toString = function () {
    return String(this.num);
};

Const.prototype.evaluate = function () {
    return this.num;
};

Const.prototype.prefix = Const.prototype.toString;
Const.prototype.postfix = Const.prototype.toString;
Const.prototype.diff = () => Const.ZERO;

Const.prototype.evaluate = function () {
    return Number(this.num);
};

function Variable(name) {
    this.name = name;
}

Variable.prototype.evaluate = function (...args) {
    return args[VARIABLE_NAMES[this.name]];
};

Variable.prototype.toString = function () {
    return this.name;
};

Variable.prototype.prefix = Variable.prototype.toString;

Variable.prototype.postfix = Variable.prototype.toString;

Variable.prototype.diff = function (x) {
    return this.name === x ? Const.ONE : Const.ZERO;
};

function Operation(f, sign, diff, args) {
    this.f = f;
    this.sign = sign;
    this.args = args;
    this.diff = (...vars) => diff(...args, ...args.map(expr => expr.diff(...vars)));
}
Operation.prototype.evaluate = function (...vars) {
    return this.f(...this.args.map(expr => expr.evaluate(...vars)));
};

Operation.prototype.toString = function () {
    let result = "";
    result = this.args.map(x => x.toString()).join(" ");
    result = result.concat(" ").concat(this.sign);
    return result
};

Operation.prototype.prefix = function () {
    let result = ['(', this.sign, ' '];
    result.push(this.args.map(expr => expr.prefix()).join(' '));
    result.push(')');
    return result.join('');
};

Operation.prototype.postfix = function () {
    let result = ['('];
    result.push(this.args.map(expr => expr.postfix()).join(' '));
    result.push(' ', this.sign, ')');
    return result.join('');
};

const operation = (f, sign, diff) => {
    resultFunction = function (...args) {
        Operation.call(this, f, sign, diff, args);
    };
    resultFunction.arity = f.length;
    return resultFunction;
};

const diff_add = (a, b, da, db) => new Add(da, db);

const Add = operation((x, y) => x + y, "+", diff_add);

Add.prototype = Object.create(Operation.prototype);
Add.prototype.constructor = Add;

const diff_subtract = (a, b, da, db) => new Subtract(da, db);

const Subtract = operation((a, b) => a - b, "-",
    diff_subtract);

Subtract.prototype = Object.create(Operation.prototype);
Subtract.prototype.constructor = Subtract;

const diff_pow = (a, b, da, db) => {
    let in1 = new Log(Const.E, a);
    let din1 = diff_log(Const.E, a, Const.ZERO, da);
    let second = diff_multiply(in1, b, din1, db);
    return new Multiply(
        new Pow(a, b),
        second
    );
}

const Pow = operation(Math.pow, "pow", diff_pow);

Pow.prototype = Object.create(Operation.prototype);
Pow.prototype.constructor = Pow;

const log = (x, y) => Math.log(Math.abs(y)) / Math.log(Math.abs(x));

const diff_log = (a, b, da, db) => {
    let den = new Log(Const.E, a);
    return new Divide(
        new Subtract(
            new Multiply(
                new Multiply(
                    new Divide(
                        Const.ONE,
                        b
                    ),
                    db
                ),
                new Log(Const.E, a)
            ),
            new Multiply(
                new Log(Const.E, b),
                new Multiply(
                    new Divide(
                        Const.ONE,
                        a
                    ),
                    da
                )
            )
        ),
        new Multiply(
            den,
            den
        )
    );
}

const Log = operation(log, "log", diff_log);

Log.prototype = Object.create(Operation.prototype);
Log.prototype.constructor = Log;

const diff_multiply = (a, b, da, db)  =>
    new Add(
        new Multiply(da, b),
        new Multiply(a, db)
    )

const Multiply = operation((x, y) => x * y, "*", diff_multiply);

Multiply.prototype = Object.create(Operation.prototype);
Multiply.prototype.constructor = Multiply;

const diff_divide = (a, b, da, db) =>
    new Divide(
        new Subtract(
            new Multiply(da, b),
            new Multiply(a, db)
        ),
        new Multiply(b, b)
    );

const Divide = operation((x, y) => x / y, "/", diff_divide
);

Divide.prototype = Object.create(Operation.prototype);
Divide.prototype.constructor = Divide;

const diff_negate = (a, da) => new Negate(da);

const Negate = operation(x => -x, "negate", diff_negate);

Negate.prototype = Object.create(Operation.prototype);
Negate.prototype.constructor = Negate;

const mean = (...args) => args.reduce((sum, x) => sum + x, 0) / args.length;

const diff_mean = (...exprs) => new Mean(...exprs.slice(exprs.length / 2));

const Mean = operation(mean, "mean", diff_mean);

Mean.prototype = Object.create(Operation.prototype);
Mean.prototype.constructor = Mean;

function disp(...args) {
    return mean(...args.map(x => Math.pow(x, 2))) - Math.pow(mean(...args), 2);
}

const diff_var = (...exprs) => {
    let args = exprs.slice(0, exprs.length / 2);
    let dargs = exprs.slice(exprs.length / 2);
    let tmean = new Mean(...args);
    let tdmean = new Mean(...dargs);
    let multiplied = [];
    for (let i = 0; i < exprs.length / 2; ++i) {
        multiplied.push(new Multiply(args[i], dargs[i]));
    }
    let first = new Mean(...multiplied);
    let second = new Multiply(tmean, tdmean);
    return new Multiply(new Const(2), new Subtract(first, second));
}

const Var = operation(disp, "var", diff_var);

Var.prototype = Object.create(Operation.prototype);
Var.prototype.constructor = Var;

const SIGN_TO_OPERATION = {
    "pow": Pow,
    "log": Log,
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "mean": Mean,
    "var": Var
};

const indefinite_arity = 0;

const parse = (string) => {
    const array = string.trim().split(/\s+/);
    let stack = [];
    const parseValue = value => {
        if (VARIABLE_NAMES[value] !== undefined) {
            stack.push(new Variable(value));
        } else if (SIGN_TO_OPERATION[value] === undefined) {
            stack.push(new Const(Number(value)));
        } else {
            let op = SIGN_TO_OPERATION[value];
            let arity = op.arity;
            stack.push(
                new op(...stack.splice(stack.length - arity, arity))
            );
        }
    };
    array.map(parseValue);
    return stack.pop();
};

function SyntaxError(message) {
    this.message = message;
}

SyntaxError.prototype = Object.create(Error.prototype);
SyntaxError.prototype.name = "SyntaxError";
SyntaxError.prototype.constructor = SyntaxError;

const parseExpression = function (expression, isPost) {

    expression = expression.trim();

    if (expression.length === 0) {
        throw new SyntaxError("empty string in input stream")
    }

    let pos = 0;

    function skipWhitespaces() {
        while (/\s+/.test(expression.charAt(pos))) {
            pos++;
        }
    }

    function parseSubExpression() {
        skipWhitespaces();
        let result;
        if (expression.charAt(pos) === '(') {
            result = parseOperation();
        } else {
            result = parseToken();
        }
        skipWhitespaces();
        return result;
    }

    function parseOperation() {
        pos++;
        let splited = [];
        while (expression.charAt(pos) !== ')' && pos < expression.length) {
            splited.push(parseSubExpression());
            skipWhitespaces();
        }
        if (expression.charAt(pos) !== ')') {
            throw new SyntaxError("incorrect parenthesis sequence: " + pos);
        }
        pos++;

        flag = false
        for (let i = 0; i < splited.length; i++) {
            if (typeof splited[i] === "string") {
                if (flag) {
                    throw new SyntaxError("impossible operation argument \'" + splited[i] + "\': " + pos);
                }
                flag = true;
            }
        }
        let sign;
        if (!isPost) {
            sign = splited[0];
        } else {
            sign = splited[splited.length - 1];
        }
        if (SIGN_TO_OPERATION[sign] === undefined) {
            if (sign === undefined) {
                sign = "EMPTY_PLACE";
            }
            throw new SyntaxError("incorrect operation sign \'" + sign + "\':" + pos)
        }
        let operation = SIGN_TO_OPERATION[sign];
        let arity = operation.arity;
        let args;
        if (!isPost) {
            args = splited.splice(1);
        } else {
            args = splited.splice(0, splited.length - 1);
        }
        if (arity !== args.length && arity !== indefinite_arity) {
            throw new SyntaxError("wrong number of arguments for \'"+ sign + "\': " + pos);
        }
        return new operation(...args);
    }

    function parseToken() {
        let substart = pos;
        while (pos < expression.length
        && !/\s+/.test(expression.charAt(pos))
        && expression.charAt(pos) !== '('
        && expression.charAt(pos) !== ')') {
            pos++;
        }
        let value = expression.substring(substart, pos);
        if (VARIABLE_NAMES[value] !== undefined) {
            return new Variable(value);
        } else if (!isNaN(Number(value))) {
            return new Const(Number(value));
        } else if (SIGN_TO_OPERATION[value]) {
            return value;
        }
        throw new SyntaxError("unexpected expression token \'" + value + "\': " + pos);
    }

    let res = parseSubExpression();
    if (pos !== expression.length) {
        throw new SyntaxError("end of expression expected: " + pos);
    }
    return res;
};

const parsePrefix = expression => parseExpression(expression, false);
const parsePostfix = expression => parseExpression(expression, true);
