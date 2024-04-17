package expression.generic;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        String mode;
        switch (args[0]) {
            case "-i" -> mode = "i";
            case "-d" -> mode = "d";
            default -> mode = "bi";
        }
        System.out.println(Arrays.toString(new GenericTabulator().tabulate(mode, args[1], -2, 2, -2, 2, -2, 2)));
    }
}
