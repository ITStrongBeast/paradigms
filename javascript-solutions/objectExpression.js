"use strict"

let operation = {}

function makeOp(symbol, oper, dif) {
    const fab = function Op(...args) {
        return {
            symbol: symbol,
            arn: oper.length,
            evaluate: function (x, y, z) {
                return oper(...args.map(item => item.evaluate(x, y, z)))
            },
            toString: function () {
                return args.map(item => item.toString()).join(" ") + " " + symbol
            },
            diff: function (x) {
                return dif(x, ...args)
            },
            prefix: function () {
                return "(" + symbol + " " + args.map(item => item.prefix()).join(" ") + ")"
            },
            postfix: function () {
                return "(" + args.map(item => item.postfix()).join(" ") + " " + symbol + ")"
            }
        }
    }
    fab.prototype = Object.create(makeOp)
    operation[symbol] = fab
    return fab
}

const Add = makeOp(
    "+",
    (left, right) => left + right,
    (x, left, right) => new Add(left.diff(x), right.diff(x))
)

const Subtract = makeOp(
    "-",
    (left, right) => left - right,
    (x, left, right) => new Subtract(left.diff(x), right.diff(x))
)

const Multiply = makeOp(
    "*",
    (left, right) => left * right,
    (x, left, right) => new Add(new Multiply(left.diff(x), right), new Multiply(left, right.diff(x)))
)

const Divide = makeOp(
    "/",
    (left, right) => left / right,
    (x, left, right) => new Divide(new Subtract(new Multiply(left.diff(x), right), new Multiply(left, right.diff(x))), new Multiply(right, right))
)

const ArcTan2 = makeOp(
    "atan2",
    (left, right) => Math.atan2(left, right),
    (x, left, right) => new ArcTan(new Divide(left, right)).diff(x)
)


const Negate = makeOp(
    "negate",
    (value) => -value,
    (x, value) => new Negate(value.diff(x))
)

const Sin = makeOp(
    "sin",
    (value) => Math.sin(value),
    (x, value) => new Multiply(new Cos(value), value.diff(x))
)

const Cos = makeOp(
    "cos",
    (value) => Math.cos(value),
    (x, value) => new Multiply(new Negate(new Sin(value)), value.diff(x))
)

const ArcTan = makeOp(
    "atan",
    (value) => Math.atan(value),
    (x, value) => new Multiply(new Divide(one, new Add(one, new Multiply(value, value))), value.diff(x))
)

const one = new Const(1)
const zero = new Const(0)

const Sum = makeOp(
    "sum",
    (...args) => args.reduce(),
    (x, ...args) => args.map(item => item.diff(x)).reduce((result, item) => new Add(result, item))
    )

const Mean = makeOp("mean",
    (...args) => args.map(item => item / args.length).reduce((result, item) => result + item),
    (x, ...args) => new Multiply(new Divide(one, new Const(args.length)),
        args.length > 1 ? new Sum(...args).diff(x) : args[0].diff(x)))

const Var = makeOp("var",
    (...args) => args.map(item => item ** 2 / args.length).reduce((result, item) => result + item) -
        args.map(item => item / args.length).reduce((result, item) => result + item) ** 2,
    (x, ...args) => new Subtract(new Mean(...args.map(item => new Multiply(item, item))).diff(x),
        new Multiply(new Multiply(new Const(2), new Mean(...args)), new Mean(...args).diff(x))))


function Const(value) {
    return {
        value: value,
        evaluate: function () {
            return value
        },
        diff: function () {
            return zero
        },
        toString: function () {
            return String(value)
        },
        prefix: function () {
            return String(value)
        },
        postfix: function () {
            return String(value)
        }
    }
}

const value = i => (...args) => args[i]
const variables = {
    "x": value(0),
    "y": value(1),
    "z": value(2)
}

function Variable(value) {
    return {
        value: value,
        evaluate: variables[value],
        diff: function (x) {
            return x === value ? one : zero
        },
        toString: function () {
            return value
        },
        prefix: function () {
            return value
        },
        postfix: function () {
            return value
        }
    }
}

function parse(expression) {
    const stack = [], items = expression.split(/\s+/).filter(i => !!i);
    for (const item of items) {
        if (operation.hasOwnProperty(item)) {
            stack.push(operation[item](...stack.splice(-1 * operation[item]().arn)))
        } else if (variables.hasOwnProperty(item)) {
            stack.push(new Variable(item))
        } else {
            stack.push(new Const(Number(item)))
        }
    }
    return stack.pop()
}

function MyException(name, message) {
    this.name = name
    this.message = name + ": " + message
}

MyException.prototype = Error.prototype

function fabricExceptoin(name, message) {
    function Exception(item = "") {
        MyException.call(this, name, message + item)
    }
    Exception.prototype = MyException.prototype
    return Exception
}

const MyParseException = fabricExceptoin("MyParseException", "Invalid character: -> ")
const MyOperationException = fabricExceptoin("MyOperationException", "There are not enough operands for the operation: -> ")
const MyNoOperationException = fabricExceptoin("MyNoOperationException", "Not enough operations")
const MyNoExpressionException = fabricExceptoin("MyNoExpressionException", "The expression is empty")
const MyBracketsException = fabricExceptoin("MyBracketsException", "There are not enough: -> ")

function abstractParse(expression) {
    const stackOperands = [], stackOperation = []
    const items = expression.split(/\s+|([()])/).filter(i => !!i)
    if (items.length === 0) {
        throw new MyNoExpressionException()
    }
    let count = 1;
    for (const item of items) {
        if (item === "(") {
            stackOperands.push("(")
        } else if (item === ")") {
            if (stackOperation.length === 0) {
                throw new MyNoOperationException()
            }
            if (stackOperands[0] !== "(") {
                throw new MyBracketsException("(")
            }
            const oper = stackOperands.splice(-(stackOperands.length - stackOperands.lastIndexOf("(") - 1))
            if (oper.length === 0 || oper[0] === "(" || stackOperation.at(-1)[0]().arn !== 0 && oper.length !== stackOperation.at(-1)[0]().arn) {
                const buff = stackOperation.pop()
                throw new MyOperationException(buff[0]().symbol + " In " + buff[1] + " operation ")
            }
            const obj = stackOperation.pop()[0](...oper)
            stackOperands.pop()
            stackOperands.push(obj)
        } else if (operation.hasOwnProperty(item)) {
            stackOperation.push([operation[item], count++])
        } else if (variables.hasOwnProperty(item)) {
            stackOperands.push(new Variable(item))
        } else if (isFinite(item)) {
            stackOperands.push(new Const(Number(item)))
        } else {
            throw new MyParseException(item)
        }
    }
    if (stackOperands[0] === "(") {
        throw new MyBracketsException(")")
    }
    if (stackOperation.length !== 0) {
        throw new MyOperationException(stackOperation.pop()().symbol)
    }
    if (stackOperands.length > 1) {
        throw new MyNoOperationException()
    }
    return stackOperands.pop()
}

const parsePrefix = (expression) => abstractParse(expression)
const parsePostfix = (expression) => abstractParse(expression)
