package expression.exceptions;

import expression.UnaryActions;
import expression.Unification;

import java.util.List;

public class CheckedNegate extends UnaryActions {
    private final Unification expression;

    public CheckedNegate(Unification expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x) {
        if (-1 * expression.evaluate(x) == Integer.MIN_VALUE) throw new ExpressionException("overflow");
        return -1 * expression.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (-1 * expression.evaluate(x, y, z) == Integer.MIN_VALUE) throw new ExpressionException("overflow");
        return -1 * expression.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }

    @Override
    public int binaryOperation(int g, int h) {
        return 0;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        if (-1 * expression.evaluate(variables) == Integer.MIN_VALUE) throw new ExpressionException("overflow");
        return -1 * expression.evaluate(variables);
    }
}