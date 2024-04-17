package expression.generic;

import expression.exceptions.ExpressionException;

public class IntegerGeneric implements Generics<Integer>{

    @Override
    public Integer convert(int a) {
        return a;
    }

    @Override
    public Integer unaryMin(Integer result) {
        if (result == Integer.MIN_VALUE) throw new ExpressionException("overflow");
        return -1 * result;
    }

    @Override
    public Integer add(Integer left_result, Integer right_result) {
        if (right_result < 0 && left_result < 0 && right_result < Integer.MIN_VALUE - left_result ||
                right_result > 0 && left_result > 0 && right_result > Integer.MAX_VALUE - left_result)
            throw new ExpressionException("overflow");
        return left_result + right_result;
    }

    @Override
    public Integer subtract(Integer left_result, Integer right_result) {
        if (right_result > 0 && left_result < Integer.MIN_VALUE + right_result ||
                right_result < 0 && left_result > Integer.MAX_VALUE + right_result)
            throw new ExpressionException("overflow");
        return left_result - right_result;
    }

    @Override
    public Integer multiply(Integer left_result, Integer right_result) {
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

    @Override
    public Integer divide(Integer left_result, Integer right_result) {
        if (right_result == 0) throw new ExpressionException("division by zero");
        if (left_result == Integer.MIN_VALUE && right_result == -1) {
            throw new ExpressionException("overflow");
        }
        return left_result / right_result;
    }
}
