public class LogicalAnd implements Expression {
    private final Expression left;
    private final Expression right;
    private final boolean bracket;

    public LogicalAnd(boolean bracket, Expression left, Expression right) {
        this.bracket = bracket;
        this.left = left;
        this.right = right;
    }

    @Override
    public String getMessage() {
        return bracket ? "(" + left.getMessage() + " & " + right.getMessage() + ")"
                : left.getMessage() + " & " + right.getMessage();
    }
}
