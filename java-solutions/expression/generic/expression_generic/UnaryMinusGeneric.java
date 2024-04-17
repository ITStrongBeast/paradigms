package expression.generic.expression_generic;



import expression.generic.Generics;

import java.util.List;
import java.util.Objects;

public class UnaryMinusGeneric<T> extends UnaryActionsGeneric<T> {
    private final UnificationGeneric<T> expression;
    private final Generics<T> gen;
    public UnaryMinusGeneric(Generics<T> gen, UnificationGeneric<T> expression){
        this.expression = expression;
        this.gen = gen;
    }

    @Override
    public T evaluate(T x, T y, T z){
        return gen.unaryMin(expression.evaluate(x, y, z));
    }

    @Override
    public String toString(){return "-(" + expression.toString() + ")";}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryMinusGeneric<?> that = (UnaryMinusGeneric<?>) o;
        return Objects.equals(expression, that.expression) && Objects.equals(gen, that.gen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, gen);
    }

    @Override
    public T binaryOperation(T g, T h) {
        return null;
    }

    @Override
    public String print(UnificationGeneric<T> g, UnificationGeneric<T> h) {
        return null;
    }
}
