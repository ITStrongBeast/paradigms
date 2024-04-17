package expression.exceptions;

import expression.Actions;
import expression.Unification;

public class CheckedAdd extends Actions {
    public CheckedAdd(Unification left_expression, Unification right_expression) {
        super(left_expression, "+", right_expression);
    }

    @Override
    public int binaryOperation(int left_result, int right_result) {
        if (right_result < 0 && left_result < 0 && right_result < Integer.MIN_VALUE - left_result ||
                right_result > 0 && left_result > 0 && right_result > Integer.MAX_VALUE - left_result)
            throw new ExpressionException("overflow");
        return left_result + right_result;
    }
}
