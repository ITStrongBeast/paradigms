package expression.generic;

import expression.exceptions.ExpressionException;

public class UIntegerGeneric implements Generics<Integer>{

    @Override
    public Integer convert(int a) {
        return a;
    }

    @Override
    public Integer unaryMin(Integer result) {
        return -1 * result;
    }

    @Override
    public Integer add(Integer left_result, Integer right_result) {
        return left_result + right_result;
    }

    @Override
    public Integer subtract(Integer left_result, Integer right_result) {
        return left_result - right_result;
    }

    @Override
    public Integer multiply(Integer left_result, Integer right_result) {
        return left_result * right_result;
    }

    @Override
    public Integer divide(Integer left_result, Integer right_result) {
        if (right_result == 0) throw new ExpressionException("division by zero");
        return left_result / right_result;
    }
}