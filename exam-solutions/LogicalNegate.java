import java.util.Objects;

public class LogicalNegate implements Expression {
    private final Expression expression;

    public LogicalNegate(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String getMessage() {
        String message = expression.getMessage();
        if (Objects.equals(message, "0")) {
            return "1";
        }
        if (Objects.equals(message, "1")) {
            return "0";
        }
        if (Character.isLetter(message.charAt(0))) {
            return "~" + message;
        }
        if (message.charAt(0) == '~') {
            return message.substring(1);
        }
        return deMorgan(message);
    }

    private String deMorgan(String message) {
        StringBuilder result = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        int brackets = 0;
        char oper = '&';
        for (int i = 1 ; i < message.length() - 1; i++) {
            if (message.charAt(i) == '(') {
                brackets++;
                continue;
            }
            if (message.charAt(i) == ')') {
                brackets--;
                continue;
            }
            if (brackets == 0 & (message.charAt(i) == '&' || message.charAt(i) == '|')) {
                oper = message.charAt(i);
                break;
            }
            buffer.append(message.charAt(i));
        }
        addToResult(result, buffer);
        result.append(oper == '&' ? " | " : " & ");
        int len = buffer.length() + 3;
        buffer.setLength(0);
        buffer.append(message, len, message.length());
        addToResult(result, buffer);
        return result.toString();
    }

    private void addToResult(StringBuilder result, StringBuilder buffer) {
        if (Objects.equals(buffer.toString().charAt(0), '0')) {
            result.append("1");
        } else if (Objects.equals(buffer.toString().charAt(0), '1')) {
            result.append("0");
        } else {
            result.append("~").append(buffer, 0, buffer.length() - 1);
        }
    }
}
