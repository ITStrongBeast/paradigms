package expression.generic.expression_generic;

import expression.generic.Generics;

public class MultiplyGeneric<T> extends ActionsGeneric<T> {
    private final Generics<T> gen;

    public MultiplyGeneric(Generics<T> gen, UnificationGeneric<T> left_expression, UnificationGeneric<T> right_expression){
        super(left_expression, right_expression);
        this.gen = gen;
    }

    public T binaryOperation(T left_result, T right_result){
        return  gen.multiply(left_result, right_result);
    }

    @Override
    public String print(UnificationGeneric<T> left_result, UnificationGeneric<T> right_result) {
        return left_result.toString() + " * " + right_result.toString();
    }
}