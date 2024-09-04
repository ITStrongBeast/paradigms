public class Bool implements Expression {
    private final String message;

    public Bool(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
