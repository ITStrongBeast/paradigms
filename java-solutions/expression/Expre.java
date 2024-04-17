package expression;

import java.util.Objects;

public abstract class Expre implements Unification {
    private  int result;
    private String var;

    public Expre(int r){
        this.result = r;
    }

    public Expre(String t){
        this.var = t;
    }

    @Override
    public int binaryOperation(int g, int h) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expre expre = (Expre) o;
        return result == expre.result && Objects.equals(var, expre.var);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, var);
    }
}
