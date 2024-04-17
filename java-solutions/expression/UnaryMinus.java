package expression;



import java.util.List;
import java.util.Objects;

public class UnaryMinus extends UnaryActions{
    private final Unification expression;
    public UnaryMinus(Unification expression){
        this.expression = expression;
    }
    @Override
    public int evaluate(int x){
        return -1 * expression.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z){
        return -1 * expression.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return -1 * expression.evaluate(variables);
    }

    @Override
    public String toString(){return "-(" + expression.toString() + ")";}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryMinus that = (UnaryMinus) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public int binaryOperation(int g, int h) {
        return 0;
    }
}