package expression.exceptions;

import expression.*;

import java.util.*;

public class ExpressionParser implements TripleParser, ListParser {
    private final Deque<Teg> startStack = new ArrayDeque<>();
    private final Deque<Unification> finishStack = new ArrayDeque<>();
    private final StringBuilder digit = new StringBuilder();
    private String last;

    private enum Teg {
        UNARYMINUS(600),
        L0(600),
        T0(600),
        MULTIPLY(500),
        DIVIDE(500),
        ADD(400),
        SUBTRACT(400),
        BITAND(300),
        BITXOR(200),
        BITOR(100),
        LEFTBR(0);
        private final int i;

        Teg(int i) {
            this.i = i;
        }

        private int getTeg() {
            return i;
        }
    }


    @Override
    public Unification parse(String expression, List<String> variables) {
        return parse(expression);
    }

    public Unification parse(String start) throws ParseExceptions {
        digit.setLength(0);
        startStack.clear();
        finishStack.clear();
        int k = 0, i = 0;
        last = " ";
        while (i < start.length()) {
            char symbol = start.charAt(i);
            // обработка l0, t0
            if ((symbol == 'l' || symbol == 't') && i != start.length() - 1 && start.charAt(i + 1) == '0') {
                if (i != start.length() - 2 && Character.isDigit(start.charAt(i + 2))) {
                    throw new OperationException("Incorrect input of the argument for finding zero bits: --> " + symbol + "0" + " In " + i + " position");
                }
                if (i != start.length() - 2 && ("xyz".contains(String.valueOf(start.charAt(i + 2))))) {
                    throw new OperationException("Incorrect input of the argument for finding zero bits: --> " + symbol + "0" + " In " + i + " position");
                }
                if (symbol == 'l' && start.charAt(i + 1) == '0') {
                    startStack.push(Teg.L0);
                    last = "l";
                } else {
                    startStack.push(Teg.T0);
                    last = "t";
                }
                i++;
            }
            // обработка чисел
            else if (Character.isDigit(symbol)) {
                if (Character.isDigit(last.charAt(0)))
                    throw new OperationException("Incorrect input of the argument: --> " + last + " In " + i + " position");
                digit.append(symbol);
                while (i != start.length() - 1 && Character.isDigit(start.charAt(i + 1))) {
                    i++;
                    digit.append(start.charAt(i));
                }
                checkOverflow(digit.toString());
                finishStack.push(new Const(Integer.parseInt(digit.toString())));
                last = digit.toString();
                digit.setLength(0);
            }
            // обработка операций
            else if ("+*/&^|".contains(String.valueOf(symbol))) {
                if ("+*/&^|".contains(last)) {
                    throw new OperationException("There is no object for a binary operation: --> " + last + " In " + i + " position");
                }
                removeStack(String.valueOf(symbol));
            } else if (symbol == '-') {
                if (finishStack.isEmpty() || "+*/-&^|lt~".contains(last)) {
                    if (i != start.length() - 1 && Character.isDigit(start.charAt(i + 1))) {
                        digit.append("-");
                    } else {
                        startStack.push(Teg.UNARYMINUS);
                        last = "~";
                    }
                } else {
                    removeStack("-");
                }
            }
            // обработка скобок
            else if (symbol == '(') {
                k++;
                startStack.push(Teg.LEFTBR);
            } else if (symbol == ')') {
                if ("+-*/&^|".contains(last))
                    throw new OperationException("There is no object for a binary operation: --> " + last + " In " + i + " position");
                if (k == 0) throw new BracketException("There are not enough left brackets");
                while (true) {
                    Teg a = startStack.pop();
                    if (Objects.equals(a, Teg.LEFTBR)) {
                        k--;
                        break;
                    }
                    conversion(a);
                }
            }
            // обработка переменных
            else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
                finishStack.push(new Variable(String.valueOf(symbol)));
                last = String.valueOf(symbol);
            } else if (symbol == '$') {
                StringBuilder var = new StringBuilder();
                i++;
                while (i < start.length() && Character.isDigit(start.charAt(i))) {
                    var.append(start.charAt(i));
                    i++;
                }
                finishStack.push(new Variable(Integer.parseInt(var.toString())));
                i--;
                last = var.toString();
            } else if (!Character.isWhitespace(symbol)) {
                throw new ParseExceptions("Invalid character in the expression: --> " + symbol + " In " + i + " position");
            }
            i++;
        }
        if (k > 0) {
            throw new BracketException("There are not enough right brackets");
        }
        if (finishStack.isEmpty()) {
            throw new ParseExceptions("There are not enough constants and variables in the expression");
        }
        while (!startStack.isEmpty()) {
            conversion(startStack.pop());
        }
        return finishStack.pop();
    }

    private void removeStack(String s) throws ParseExceptions { // метод сортировочной станции
        last = s;
        Teg str = translation(s);
        while (!startStack.isEmpty() && startStack.peek().getTeg() >= str.getTeg()) {
            conversion(startStack.pop());
        }
        startStack.push(str);
    }

    private Teg translation(String str) {
        return switch (str) {
            case "-" -> Teg.SUBTRACT;
            case "+" -> Teg.ADD;
            case "*" -> Teg.MULTIPLY;
            case "/" -> Teg.DIVIDE;
            case "&" -> Teg.BITAND;
            case "^" -> Teg.BITXOR;
            case "|" -> Teg.BITOR;
            default -> Teg.UNARYMINUS;
        };
    }


    private void conversion(Teg expression) throws ParseExceptions {
        if (finishStack.isEmpty())
            throw new OperationException("There is no object for a unary operation: --> " + last);
        Unification stack_element = finishStack.pop();
        switch (expression) {
            case UNARYMINUS -> finishStack.push(new CheckedNegate(stack_element));
            case L0 -> finishStack.push(new HighestZeroBit(stack_element));
            case T0 -> finishStack.push(new LowerZeroBit(stack_element));
            default -> {
                if (finishStack.isEmpty())
                    throw new OperationException("There is no object for a binary operation: --> " + last);
                finishStack.push(switch (expression) {
                    case SUBTRACT -> new CheckedSubtract(finishStack.pop(), stack_element);
                    case ADD -> new CheckedAdd(finishStack.pop(), stack_element);
                    case DIVIDE -> new CheckedDivide(finishStack.pop(), stack_element);
                    case MULTIPLY -> new CheckedMultiply(finishStack.pop(), stack_element);
                    case BITAND -> new BitAnd(finishStack.pop(), stack_element);
                    case BITXOR -> new BitXor(finishStack.pop(), stack_element);
                    default -> new BitOr(finishStack.pop(), stack_element);
                });
            }
        }
    }

    private void checkOverflow(String str) {
        if (!(str.charAt(0) == '-') && str.length() == String.valueOf(Integer.MAX_VALUE).length() && str.compareTo(String.valueOf(Integer.MAX_VALUE)) > 0
                || str.charAt(0) == '-' && str.length() == String.valueOf(Integer.MIN_VALUE).length() && str.compareTo(String.valueOf(Integer.MIN_VALUE)) > 0) {
            throw new ExpressionException("overflow");
        }
    }
}