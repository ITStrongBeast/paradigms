package expression;

import java.util.List;
import java.util.Objects;

public class HighestZeroBit extends UnaryActions {
    private Unification expression;
    public HighestZeroBit(Unification expression){
        this.expression = expression;
    }

    @Override
    public int evaluate(int x) {
        int r = expression.evaluate(x);
        String resulrt = Integer.toBinaryString(r);
        if (r == 0) {
            return 33 - resulrt.length();
        } else{
            return 32 - resulrt.length();
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int r = expression.evaluate(x, y, z);
        String resulrt = Integer.toBinaryString(r);
        if (r == 0){
            return 33 - resulrt.length();
        } else{
            return 32 - resulrt.length();
        }
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return 0;
    }

    @Override
    public String toString(){return "l0(" + expression.toString() + ")";}

    @Override
    public int binaryOperation(int g, int h) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighestZeroBit that = (HighestZeroBit) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

}
