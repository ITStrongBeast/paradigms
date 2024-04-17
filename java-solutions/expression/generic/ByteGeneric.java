package expression.generic;

import expression.exceptions.ExpressionException;

public class ByteGeneric implements Generics<Byte> {

    @Override
    public Byte convert(int a) {
        return (byte) a;
    }

    @Override
    public Byte unaryMin(Byte result) {
        return (byte) (-1 * result);
    }

    @Override
    public Byte add(Byte left_result, Byte right_result) {
        return (byte) (left_result + right_result);
    }

    @Override
    public Byte subtract(Byte left_result, Byte right_result) {
        return (byte) (left_result - right_result);
    }

    @Override
    public Byte multiply(Byte left_result, Byte right_result) {
        return (byte) (left_result * right_result);
    }

    @Override
    public Byte divide(Byte left_result, Byte right_result) {
        if (right_result == 0) throw new ExpressionException("division by zero");
        return (byte) (left_result / right_result);
    }
}
