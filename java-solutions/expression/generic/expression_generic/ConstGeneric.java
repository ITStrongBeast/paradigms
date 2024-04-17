package expression.generic.expression_generic;

public class ConstGeneric<T> extends ExpreGeneric<T> {
    T con;

    public ConstGeneric(T con) {
        super(con);
        this.con = con;
    }

    @Override
    public String print(UnificationGeneric<T> g, UnificationGeneric<T> h) {
        return null;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return con;
    }

    @Override
    public String toString() {
        return String.valueOf(con);
    }
}
