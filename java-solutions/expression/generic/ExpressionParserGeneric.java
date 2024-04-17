package expression.generic;

import expression.*;
import expression.exceptions.*;
import expression.generic.expression_generic.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class ExpressionParserGeneric<T> {
    private final Deque<Teg> startStack = new ArrayDeque<>();
    private final Deque<UnificationGeneric<T>> finishStack = new ArrayDeque<>();
    private final StringBuilder digit = new StringBuilder();
    private Generics<T> gen;
    private String last;

    private enum Teg {
        UNARYMINUS(600),
        MULTIPLY(500),
        DIVIDE(500),
        ADD(400),
        SUBTRACT(400),
        LEFTBR(0);
        private final int i;

        Teg(int i) {
            this.i = i;
        }

        private int getTeg() {
            return i;
        }
    }


    public UnificationGeneric<T> parse(Generics<T> gen, String start) throws ParseExceptions {
        this.gen = gen;
        digit.setLength(0);
        startStack.clear();
        finishStack.clear();
        int k = 0, i = 0;
        last = " ";
        // :NOTE: Поле класса
        String op = "+*/";
        String mop = "-~";
        while (i < start.length()) {
            char symbol = start.charAt(i);
            // обработка чисел
            if (Character.isDigit(symbol)) {
                if (Character.isDigit(last.charAt(0))) {
                    throw new OperationException("Incorrect input of the argument: --> " + last + " In " + i + " position");
                }
                digit.append(symbol);
                while (i != start.length() - 1 && Character.isDigit(start.charAt(i + 1))) {
                    i++;
                    digit.append(start.charAt(i));
                }
                checkOverflow(digit.toString());
                finishStack.push(new ConstGeneric<>(gen.convert(Integer.parseInt(digit.toString()))));
                last = digit.toString();
                digit.setLength(0);
            }
            // обработка операций
            // :NOTE: Вынести в константы
            else if (op.contains(String.valueOf(symbol))) {
                removeStack(String.valueOf(symbol));
            } else if (symbol == '-') {
                if (finishStack.isEmpty() || op.contains(last) || mop.contains(last)) {
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
                finishStack.push(new VariableGeneric<>(String.valueOf(symbol)));
                last = String.valueOf(symbol);
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
            default -> Teg.UNARYMINUS;
        };
    }


    private void conversion(Teg expression) throws ParseExceptions {
        if (finishStack.isEmpty())
            throw new OperationException("There is no object for a unary operation: --> " + last);
        UnificationGeneric<T> stack_element = finishStack.pop();
        if (Objects.requireNonNull(expression) == Teg.UNARYMINUS) {
            finishStack.push(new UnaryMinusGeneric<>(gen, stack_element));
        } else {
            if (finishStack.isEmpty())
                throw new OperationException("There is no object for a binary operation: --> " + last);
            finishStack.push(switch (expression) {
                case SUBTRACT -> new SubtractGeneric<>(gen, finishStack.pop(), stack_element);
                case ADD -> new AddGeneric<>(gen, finishStack.pop(), stack_element);
                case DIVIDE -> new DivideGeneric<>(gen, finishStack.pop(), stack_element);
                default -> new MultiplyGeneric<>(gen, finishStack.pop(), stack_element);
            });
        }
    }

    private void checkOverflow(String str) {
        if (!(str.charAt(0) == '-') && str.length() == String.valueOf(Integer.MAX_VALUE).length() && str.compareTo(String.valueOf(Integer.MAX_VALUE)) > 0
                || str.charAt(0) == '-' && str.length() == String.valueOf(Integer.MIN_VALUE).length() && str.compareTo(String.valueOf(Integer.MIN_VALUE)) > 0) {
            throw new ExpressionException("overflow");
        }
    }
}
