package expression.generic;

import expression.exceptions.ExpressionException;

import java.math.BigInteger;
import java.util.Objects;

public class BigIntegerGeneric implements Generics<BigInteger>{
    @Override
    public BigInteger convert(int a) {
        return new BigInteger(Integer.toString(a));
    }

    @Override
    public BigInteger unaryMin(BigInteger result) {
        return result.multiply(BigInteger.valueOf(-1));
    }

    @Override
    public BigInteger add(BigInteger left_result, BigInteger right_result) {
        return left_result.add(right_result);
    }

    @Override
    public BigInteger subtract(BigInteger left_result, BigInteger right_result) {
        return left_result.subtract(right_result);
    }

    @Override
    public BigInteger multiply(BigInteger left_result, BigInteger right_result) {
        return left_result.multiply(right_result);
    }

    @Override
    public BigInteger divide(BigInteger left_result, BigInteger right_result) {
        if (Objects.equals(right_result, BigInteger.valueOf(0))) throw new ExpressionException("division by zero");
        return left_result.divide(right_result);
    }
}
