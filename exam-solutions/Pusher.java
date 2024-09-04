import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class Pusher {
    private final Deque<Operation> stackOperation = new ArrayDeque<>();
    private final Deque<Expression> stackOperands = new ArrayDeque<>();
    private boolean bracket = false;

    private enum Operation {
        NEGATE(600),
        LOGICALAND(400),
        LOGICALOR(200),
        LEFTBR(0);

        private final int priority;

        Operation(int priority) {
            this.priority = priority;
        }

        private int getPriority() {
            return priority;
        }
    }

    public String parse(String line) {
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                stackOperands.push(new Bool(String.valueOf(c)));
            } else if (Character.isLetter(c)) {
                stackOperands.push(new Variable(String.valueOf(c)));
            } else if (c == '(') {
                stackOperation.push(Operation.LEFTBR);
                bracket = true;
            } else if (c == ')') {
                while (true) {
                    Operation expression = stackOperation.pop();
                    if (Objects.equals(expression, Operation.LEFTBR)) {
                        break;
                    }
                    conversion(expression);
                }
            } else {
                Operation operation = switch (c) {
                    case '&' -> Operation.LOGICALAND;
                    case '|' -> Operation.LOGICALOR;
                    default -> Operation.NEGATE;
                };
                while (!stackOperation.isEmpty() && stackOperation.peek().getPriority() > operation.getPriority()) {
                    conversion(stackOperation.pop());
                }
                stackOperation.push(operation);
            }
        }
        while (!stackOperation.isEmpty()) {
            conversion(stackOperation.pop());
        }
        return stackOperands.pop().getMessage();
    }

    private void conversion(Operation expression) {
        Expression element = stackOperands.pop();
        switch (expression) {
            case LOGICALAND -> {
                stackOperands.push(new LogicalAnd(bracket, stackOperands.pop(), element));
                bracket = false;
            }
            case LOGICALOR -> {
                stackOperands.push(new LogicalOr(bracket, stackOperands.pop(), element));
                bracket = false;
            }
            default -> stackOperands.push(new LogicalNegate(element));
        }
    }
}
