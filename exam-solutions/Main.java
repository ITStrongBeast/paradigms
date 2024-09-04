import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();
            Pusher pushNegate = new Pusher();
            System.out.println(pushNegate.parse(line));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
