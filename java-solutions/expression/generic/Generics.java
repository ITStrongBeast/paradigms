package expression.generic;

public interface Generics<T> {
    T convert (int a);
    T unaryMin(T result);
    T add(T left_result, T right_result);
    T subtract(T left_result, T right_result);
    T multiply(T left_result, T right_result);
    T divide(T left_result, T right_result);
}
