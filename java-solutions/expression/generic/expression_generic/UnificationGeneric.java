package expression.generic.expression_generic;

public interface UnificationGeneric<T> {
    T binaryOperation(T g, T h);
    String print(UnificationGeneric<T> g, UnificationGeneric<T> h);
    public T evaluate(T x, T y, T z);
    public String toString();
}
