package expression.generic.expression_generic;

import java.util.List;

public class VariableGeneric<T> extends ExpreGeneric<T> {
    private final String var;
    private final T ind;
    public VariableGeneric(String c){
        super(c);
        this.var = c;
        this.ind = null;
    }

    public VariableGeneric(T c){
        super(c);
        this.ind = c;
        this.var = String.valueOf(c);
    }

    @Override
    public String print(UnificationGeneric<T> g, UnificationGeneric<T> h) {
        return null;
    }

    @Override
    public T evaluate(T x, T y, T z){
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }

    @Override
    public String toString() {
        if (Character.isDigit(var.charAt(0))){
            return "$" + var;
        }
        return var;
    }
}
