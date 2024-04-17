package expression.exceptions;

import expression.Actions;
import expression.Unification;

public class CheckedMultiply extends Actions {
    public CheckedMultiply(Unification left_expression, Unification right_expression) {
        super(left_expression, "*", right_expression);
    }

    @Override
    public int binaryOperation(int left_result, int right_result) {
        if (left_result != 0 && right_result != 0 && left_result * right_result == 0)
            throw new ExpressionException("overflow");
        if (left_result > 0 && right_result < 0 && Integer.MIN_VALUE / left_result > right_result ||
                left_result < 0 && right_result > 0 && Integer.MIN_VALUE / right_result > left_result)
            throw new ExpressionException("overflow");
        if (left_result > 0 && right_result > 0 && Integer.MAX_VALUE / right_result < left_result ||
                left_result < 0 && right_result < 0 && Integer.MAX_VALUE / right_result > left_result)
            throw new ExpressionException("overflow");
        return left_result * right_result;
    }
}
