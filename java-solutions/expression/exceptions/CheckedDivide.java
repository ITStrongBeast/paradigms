package expression.exceptions;

import expression.Actions;
import expression.Unification;

public class CheckedDivide extends Actions {
    public CheckedDivide(Unification left_expression, Unification right_expression) {
        super(left_expression, "/", right_expression);
    }

    @Override
    public int binaryOperation(int left_result, int right_result) {
        if (right_result == 0) throw new ExpressionException("division by zero");
        if (left_result == Integer.MIN_VALUE && right_result == -1)
            throw new ExpressionException("overflow");
        return left_result / right_result;
    }
}
