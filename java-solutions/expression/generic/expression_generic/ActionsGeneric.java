package expression.generic.expression_generic;

import java.util.Objects;

public abstract class ActionsGeneric<T> implements UnificationGeneric<T> {
    private final UnificationGeneric<T> left_expression;
    private final UnificationGeneric<T> right_expression;
    // :NOTE: teg в детей

    public ActionsGeneric(UnificationGeneric<T> left_expression, UnificationGeneric<T> right_expression) {
        this.left_expression = left_expression;
        this.right_expression = right_expression;
    }

    @Override
    public String toString() {
        return "(" + print(left_expression, right_expression) + ")";
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return binaryOperation(left_expression.evaluate(x, y, z), right_expression.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionsGeneric<?> that = (ActionsGeneric<?>) o;
        return Objects.equals(left_expression, that.left_expression) && Objects.equals(right_expression, that.right_expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left_expression, right_expression);
    }
}
