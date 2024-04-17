package expression.exceptions;

public class ExpressionException extends ArithmeticException {
    private final String message;

    public ExpressionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
