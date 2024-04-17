package expression;

import java.util.List;
import java.util.Objects;

public class Variable extends Expre{
    private final String var;
    private final int ind;
    public Variable(String c){
        super(c);
        this.var = c;
        this.ind = 0;
    }

    public Variable(int c){
        super(c);
        this.ind = c;
        this.var = String.valueOf(c);
    }
    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z){
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(ind);
    }

    @Override
    public String toString() {
        if (Character.isDigit(var.charAt(0))){
            return "$" + var;
        }
        return var;
    }

}
