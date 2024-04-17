package expression;

import java.util.List;
import java.util.Objects;

public abstract class Actions implements Unification {
    private final Unification left_expression;
    private final Unification right_expression;
    private final String teg;

    public Actions(Unification left_expression, String teg, Unification right_expression) {
        this.left_expression = left_expression;
        this.right_expression = right_expression;
        this.teg = teg;
    }

    @Override
    public String toString() {
        return "(" + left_expression.toString() + " " + teg + " " + right_expression.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return binaryOperation(left_expression.evaluate(x), right_expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return binaryOperation(left_expression.evaluate(x, y, z), right_expression.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return binaryOperation(left_expression.evaluate(variables), right_expression.evaluate(variables));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actions actions = (Actions) o;
        return Objects.equals(teg, actions.teg) && Objects.equals(left_expression, actions.left_expression) && Objects.equals(right_expression, actions.right_expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left_expression, right_expression, teg);
    }
}
