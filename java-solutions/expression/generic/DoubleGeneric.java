package expression.generic;

public class DoubleGeneric implements Generics<Double> {

    @Override
    public Double convert(int a) {
        return (double) a;
    }

    @Override
    public Double unaryMin(Double result) {
        return -1 * result;
    }

    @Override
    public Double add(Double left_result, Double right_result) {
        return left_result + right_result;
    }

    @Override
    public Double subtract(Double left_result, Double right_result) {
        return left_result - right_result;
    }

    @Override
    public Double multiply(Double left_result, Double right_result) {
        return left_result * right_result;
    }

    @Override
    public Double divide(Double left_result, Double right_result) {
        return left_result / right_result;
    }
}
