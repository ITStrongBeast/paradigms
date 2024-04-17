package expression.generic.expression_generic;

import java.util.Objects;

public abstract class ExpreGeneric<T> implements UnificationGeneric<T> {
    private T result;
    private String var;

    public ExpreGeneric(T r){
        this.result = r;
    }

    public ExpreGeneric(String t){
        this.var = t;
    }

    @Override
    public T binaryOperation(T g, T h) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpreGeneric<?> that = (ExpreGeneric<?>) o;
        return Objects.equals(result, that.result) && Objects.equals(var, that.var);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, var);
    }
}
