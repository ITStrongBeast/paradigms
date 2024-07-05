"use strict"

const pi = () => Math.PI
const e = () => Math.E
const cnst = (value) => () => value
const constants = {
    "pi": pi,
    "e": e
}

const variable = (teg) => variables[teg]
const value = i => (...args) => args[i]
const variables = {
    "x": value(0),
    "y": value(1),
    "z": value(2)
}


const universOp = (operation) => (...args) => (x, y, z) => operation(...args.map(item => item(x, y, z)))

const negate = universOp((value) => -value)
const square = universOp((value) => Math.abs(value) ** 2)
const sqrt = universOp((value) => Math.sqrt(value))

const add = universOp((left, right) => (left + right))
const subtract = universOp((left, right) => (left - right))
const multiply = universOp((left, right) => (left * right))
const divide = universOp((left, right) => (left / right))

const max3 = universOp((x1, x2, x3) => Math.max(Math.max(x1, x2), x3))
const med3 = universOp((x1, x2, x3) => x1 + x2 + x3 - Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3))

const min5 = universOp((x1, x2, x3, x4, x5) => Math.min(Math.min(Math.min(Math.min(x1, x2), x3), x4), x5))
const avg5 = universOp((x1, x2, x3, x4, x5) => (x1 + x2 + x3 + x4 + x5) / 5)


const operation = {
    "square": [1, square],
    "sqrt": [1, sqrt],
    "negate": [1, negate],
    "+": [2, add],
    "-": [2, subtract],
    "*": [2, multiply],
    "/": [2, divide],
    "max3": [3, max3],
    "med3": [3, med3],
    "avg5": [5, avg5],
    "min5": [5, min5]
}

function parse(expression) {
    const stack = [], items = expression.split(/\s+/).filter(i => !!i);
    for (let item of items) {
        if (operation.hasOwnProperty(item)) {
            stack.push(operation[item][1](...stack.splice(-1 * operation[item][0])))
        } else if (variables.hasOwnProperty(item)) {
            stack.push(variable(item))
        } else if (constants.hasOwnProperty(item)) {
            stack.push(constants[item])
        } else {
            stack.push(cnst(Number(item)))
        }
    }
    return stack.pop()
}