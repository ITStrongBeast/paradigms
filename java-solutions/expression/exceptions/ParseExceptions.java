package expression.exceptions;

public class ParseExceptions extends ArithmeticException {
    private final String message;

    public ParseExceptions(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
